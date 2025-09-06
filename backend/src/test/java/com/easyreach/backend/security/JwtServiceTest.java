package com.easyreach.backend.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest {
    private JwtService service;

    @BeforeEach
    void setUp() {
        service = new JwtService();
        ReflectionTestUtils.setField(service, "secretBase64", "123456789012345678901234567890123456789012345678901234567890");
        ReflectionTestUtils.setField(service, "accessTtlMinutes", 15L);
        ReflectionTestUtils.setField(service, "refreshTtlDays", 7L);
    }

    @Test
    void generateAndValidateToken() {
        com.easyreach.backend.entity.User user = new com.easyreach.backend.entity.User();
        user.setEmail("a@b.com");
        user.setCompanyUuid("c1");
        String token = service.generateAccessToken(user);
        assertThat(service.extractUsername(token)).isEqualTo("a@b.com");
        assertThat(service.extractCompanyId(token)).isEqualTo("c1");
        org.springframework.security.core.userdetails.User details =
                new org.springframework.security.core.userdetails.User("a@b.com", "", java.util.List.of());
        assertThat(service.isTokenValid(token, details)).isTrue();
    }

    @Test
    void generateAndValidateToken_usesMobileWhenEmailMissing() {
        com.easyreach.backend.entity.User user = new com.easyreach.backend.entity.User();
        user.setMobileNo("9999999999");
        user.setId("id1");
        user.setCompanyUuid("c1");
        String token = service.generateAccessToken(user);
        assertThat(service.extractUsername(token)).isEqualTo("9999999999");
        org.springframework.security.core.userdetails.User details =
                new org.springframework.security.core.userdetails.User("9999999999", "", java.util.List.of());
        assertThat(service.isTokenValid(token, details)).isTrue();
    }
}
