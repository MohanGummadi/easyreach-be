package com.easyreach.backend.auth.entity;

import com.easyreach.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserAdapter implements UserDetails {
    private final User user;

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        // assuming a single role column like "ADMIN" / "USER"
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }
    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() {
        String email = user.getEmail();
        return email != null && !email.isBlank() ? email : user.getMobileNo();
    }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return user.getIsActive() != null && user.getIsActive(); }

    public User getDomainUser() { return user; }
}
