package com.easyreach.backend.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecurityWhitelistTest {
    private final SecurityWhitelist whitelist = new SecurityWhitelist();

    @Test
    void getPathsContainsExpectedEndpoints() {
        assertThat(whitelist.getPaths()).containsExactly(
                "/login",
                "/auth/**",
                "/api/auth/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
        );
    }

    @Test
    void getPathsIsImmutable() {
        assertThatThrownBy(() -> whitelist.getPaths().add("/new"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
