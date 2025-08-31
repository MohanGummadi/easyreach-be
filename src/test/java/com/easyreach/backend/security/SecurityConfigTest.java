package com.easyreach.backend.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {
    @Mock
    private JwtAuthenticationFilter filter;
    @Mock
    private UserDetailsService userDetailsService;


    @Test
    void authenticationProvider_usesInjectedService() throws Exception {
        SecurityConfig config = new SecurityConfig(filter, userDetailsService);
        DaoAuthenticationProvider provider = (DaoAuthenticationProvider) config.authenticationProvider();

        Field field = DaoAuthenticationProvider.class.getDeclaredField("userDetailsService");
        field.setAccessible(true);
        UserDetailsService injected = (UserDetailsService) field.get(provider);

        assertThat(injected).isEqualTo(userDetailsService);
    }

}
