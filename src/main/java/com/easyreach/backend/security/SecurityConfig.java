package com.easyreach.backend.security;

import com.easyreach.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService; // injected CustomUserDetailsService
    private final SecurityWhitelist securityWhitelist;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Configuring SecurityFilterChain");
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(securityWhitelist.getPaths().toArray(String[]::new)).permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(e -> e.authenticationEntryPoint(htmlAwareAuthenticationEntryPoint()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        SecurityFilterChain chain = http.build();
        log.debug("SecurityFilterChain configured");
        return chain;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        log.debug("Initializing AuthenticationProvider");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // our bean
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        log.debug("AuthenticationProvider initialized");
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        log.debug("Retrieving AuthenticationManager");
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("Providing PasswordEncoder");
        return NoOpPasswordEncoder.getInstance(); // matches your stored password hashes
    }

    @Bean
    public AuthenticationEntryPoint htmlAwareAuthenticationEntryPoint() {
        return new HtmlAwareAuthenticationEntryPoint();
    }
}
