package com.easyreach.backend.auth.service;

import com.easyreach.backend.auth.entity.UserAdapter;
import com.easyreach.backend.entity.User;
import com.easyreach.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUserByUsername_returnsAdapter() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));

        UserAdapter adapter = (UserAdapter) service.loadUserByUsername("test@example.com");
        assertThat(adapter.getUsername()).isEqualTo("test@example.com");
    }

    @Test
    void loadUserByUsername_notFound() {
        when(userRepository.findByEmailIgnoreCase("missing@example.com")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.loadUserByUsername("missing@example.com"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
