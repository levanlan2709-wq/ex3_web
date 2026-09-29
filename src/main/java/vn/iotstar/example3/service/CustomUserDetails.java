package vn.iotstar.example3.service;

import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.iotstar.example3.entity.User;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final User user;
    public CustomUserDetails(User user) { this.user = user; }

    public Long getId() { return user.getId(); }
    public String getEmail() { return user.getEmail(); }
    public String getFullName() { return user.getFullName(); }
    public String getImages() { return user.getImages(); }
    public String getRole() { return user.getRole().getName(); }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().getName()));
    }
    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getUsername(); }
    @Override public boolean isEnabled() { return user.isEnabled(); }
}
