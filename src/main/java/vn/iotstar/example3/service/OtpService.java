package vn.iotstar.example3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iotstar.example3.entity.OtpToken;
import vn.iotstar.example3.repository.OtpTokenRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpTokenRepository repository;
    private final MailService mailService;
    private final SecureRandom random = new SecureRandom();

    public void createAndSend(String email, String purpose) {
        String otp = String.format("%06d", random.nextInt(1_000_000));
        OtpToken token = new OtpToken();
        token.setEmail(email);
        token.setOtp(otp);
        token.setPurpose(purpose);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        repository.save(token);
        mailService.sendOtp(email, otp, purpose);
    }

    public boolean verify(String email, String otp, String purpose) {
        return repository.findTopByEmailAndPurposeAndUsedFalseOrderByIdDesc(email, purpose)
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .filter(t -> t.getOtp().equals(otp))
                .map(t -> {
                    t.setUsed(true);
                    repository.save(t);
                    return true;
                }).orElse(false);
    }
}
