package vn.iotstar.example3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String from;

    public void sendOtp(String email, String otp, String purpose) {
        if (from == null || from.isBlank()) {
            System.out.println("[DEV OTP] " + purpose + " - " + email + ": " + otp);
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Example 3 - Mã OTP");
        message.setText("Mã OTP của bạn là: " + otp + ". Mã có hiệu lực 5 phút.");
        mailSender.send(message);
    }
}
