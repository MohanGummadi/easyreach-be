package com.easyreach.backend.security;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Defines URL patterns that should bypass JWT authentication.
 */
@Component
public class SecurityWhitelist {
    private final List<String> paths = List.of(
            "/login",
            "/auth/**",
            "/api/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    );

    public List<String> getPaths() {
        return paths;
    }
}
