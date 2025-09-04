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
    void loadUserByUsername_returnsAdapter_whenEmailFound() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));

        UserAdapter adapter = (UserAdapter) service.loadUserByUsername("test@example.com");
        assertThat(adapter.getUsername()).isEqualTo("test@example.com");
    }

    @Test
    void loadUserByUsername_returnsAdapter_whenMobileFound() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmailIgnoreCase("9999999999")).thenReturn(Optional.empty());
        when(userRepository.findByMobileNo("9999999999")).thenReturn(Optional.of(user));

        UserAdapter adapter = (UserAdapter) service.loadUserByUsername("9999999999");
        assertThat(adapter.getUsername()).isEqualTo("test@example.com");
    }

    @Test
    void loadUserByUsername_returnsAdapter_whenIdFound() {
        User user = new User();
        user.setId("u1");
        when(userRepository.findByEmailIgnoreCase("u1")).thenReturn(Optional.empty());
        when(userRepository.findByMobileNo("u1")).thenReturn(Optional.empty());
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));

        UserAdapter adapter = (UserAdapter) service.loadUserByUsername("u1");
        assertThat(adapter.getUsername()).isEqualTo("u1");
    }
    @Test  
    void loadUserByUsername_returnsAdapter_whenMobileFoundWithoutEmail() {
        User user = new User();
        user.setMobileNo("8888888888");
        when(userRepository.findByEmailIgnoreCase("8888888888")).thenReturn(Optional.empty());
        when(userRepository.findByMobileNo("8888888888")).thenReturn(Optional.of(user));

        UserAdapter adapter = (UserAdapter) service.loadUserByUsername("8888888888");
        assertThat(adapter.getUsername()).isEqualTo("8888888888");
    }

    @Test
    void loadUserByUsername_notFound() {
        when(userRepository.findByEmailIgnoreCase("missing@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByMobileNo("missing@example.com")).thenReturn(Optional.empty());
        when(userRepository.findById("missing@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername("missing@example.com"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
