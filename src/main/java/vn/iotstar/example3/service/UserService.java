package vn.iotstar.example3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iotstar.example3.dto.*;
import vn.iotstar.example3.entity.*;
import vn.iotstar.example3.mapper.UserMapper;
import vn.iotstar.example3.repository.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public User register(RegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) throw new IllegalArgumentException("Username đã tồn tại");
        if (userRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("Email đã tồn tại");
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow();
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setFullName(dto.getFullName());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setEnabled(false);
        u.setRole(role);
        return userRepository.save(u);
    }

    public void enableByEmail(String email) {
        User u = userRepository.findByEmail(email).orElseThrow();
        u.setEnabled(true);
        userRepository.save(u);
    }

    public void resetPassword(String email, String password) {
        User u = userRepository.findByEmail(email).orElseThrow();
        u.setPassword(encoder.encode(password));
        userRepository.save(u);
    }

    public Page<UserDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> result = (keyword == null || keyword.isBlank())
                ? userRepository.findAll(pageable)
                : userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
                    keyword, keyword, keyword, pageable);
        return result.map(mapper::toDto);
    }

    public UserDTO find(Long id) { return mapper.toDto(userRepository.findById(id).orElseThrow()); }
    public long count() { return userRepository.count(); }

    public void update(UserDTO dto) {
        User u = userRepository.findById(dto.getId()).orElseThrow();
        mapper.updateEntity(dto, u);
        if (dto.getRoleId() != null) u.setRole(roleRepository.findById(dto.getRoleId()).orElseThrow());
        userRepository.save(u);
    }

    public void delete(Long id) { userRepository.deleteById(id); }
}
