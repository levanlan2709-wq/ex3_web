package vn.iotstar.example3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iotstar.example3.entity.Role;
import vn.iotstar.example3.entity.User;
import vn.iotstar.example3.repository.RoleRepository;
import vn.iotstar.example3.repository.UserRepository;

@SpringBootApplication
public class Example3Application {
    public static void main(String[] args) {
        SpringApplication.run(Example3Application.class, args);
    }

    @Bean
    CommandLineRunner seed(RoleRepository roleRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

            if (!userRepository.existsByEmail("admin@gmail.com")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setFullName("Administrator");
                admin.setEnabled(true);
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }
}
