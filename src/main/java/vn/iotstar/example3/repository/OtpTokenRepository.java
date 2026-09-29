package vn.iotstar.example3.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.example3.entity.OtpToken;
import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findTopByEmailAndPurposeAndUsedFalseOrderByIdDesc(String email, String purpose);
}
