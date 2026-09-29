package vn.iotstar.example3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.example3.dto.UserDTO;
import vn.iotstar.example3.repository.RoleRepository;
import vn.iotstar.example3.service.UserService;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping
    public String list(@RequestParam(defaultValue="") String keyword,
                       @RequestParam(defaultValue="0") int page, Model model) {
        Page<UserDTO> data = userService.search(keyword, page, 5);
        model.addAttribute("data", data);
        model.addAttribute("keyword", keyword);
        return "admin/user-list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.find(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/user-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute UserDTO user) {
        userService.update(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
