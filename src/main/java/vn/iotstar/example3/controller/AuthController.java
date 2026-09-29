package vn.iotstar.example3.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.example3.dto.RegisterDTO;
import vn.iotstar.example3.repository.UserRepository;
import vn.iotstar.example3.service.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final OtpService otpService;

    @GetMapping("/login")
    public String login() { return "auth/login"; }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result,
                           HttpSession session, RedirectAttributes ra) {
        if (result.hasErrors()) return "auth/register";
        try {
            userService.register(registerDTO);
            otpService.createAndSend(registerDTO.getEmail(), "REGISTER");
            session.setAttribute("registerEmail", registerDTO.getEmail());
            return "redirect:/verify-register";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/verify-register")
    public String verifyRegister() { return "auth/verify-register"; }

    @PostMapping("/verify-register")
    public String verifyRegister(@RequestParam String otp, HttpSession session, RedirectAttributes ra) {
        String email = (String) session.getAttribute("registerEmail");
        if (email != null && otpService.verify(email, otp, "REGISTER")) {
            userService.enableByEmail(email);
            session.removeAttribute("registerEmail");
            ra.addFlashAttribute("message", "Xác nhận thành công. Hãy đăng nhập.");
            return "redirect:/login";
        }
        ra.addFlashAttribute("error", "OTP sai hoặc đã hết hạn");
        return "redirect:/verify-register";
    }

    @GetMapping("/forgot-password")
    public String forgot() { return "auth/forgot-password"; }

    @PostMapping("/forgot-password")
    public String forgot(@RequestParam String email, HttpSession session, RedirectAttributes ra) {
        if (userRepository.findByEmail(email).isEmpty()) {
            ra.addFlashAttribute("error", "Không tìm thấy email");
            return "redirect:/forgot-password";
        }
        otpService.createAndSend(email, "RESET_PASSWORD");
        session.setAttribute("resetEmail", email);
        return "redirect:/verify-reset";
    }

    @GetMapping("/verify-reset")
    public String verifyReset() { return "auth/verify-reset"; }

    @PostMapping("/verify-reset")
    public String verifyReset(@RequestParam String otp, HttpSession session, RedirectAttributes ra) {
        String email = (String) session.getAttribute("resetEmail");
        if (email != null && otpService.verify(email, otp, "RESET_PASSWORD")) {
            session.setAttribute("resetVerified", true);
            return "redirect:/reset-password";
        }
        ra.addFlashAttribute("error", "OTP sai hoặc đã hết hạn");
        return "redirect:/verify-reset";
    }

    @GetMapping("/reset-password")
    public String resetPassword(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute("resetVerified"))
                ? "auth/reset-password" : "redirect:/forgot-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String password, HttpSession session, RedirectAttributes ra) {
        if (!Boolean.TRUE.equals(session.getAttribute("resetVerified"))) return "redirect:/forgot-password";
        String email = (String) session.getAttribute("resetEmail");
        userService.resetPassword(email, password);
        session.removeAttribute("resetEmail");
        session.removeAttribute("resetVerified");
        ra.addFlashAttribute("message", "Đổi mật khẩu thành công");
        return "redirect:/login";
    }
}
