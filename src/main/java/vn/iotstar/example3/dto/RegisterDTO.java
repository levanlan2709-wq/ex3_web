package vn.iotstar.example3.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank private String username;
    @NotBlank @Email private String email;
    @NotBlank private String fullName;
    @NotBlank @Size(min = 6) private String password;
}
