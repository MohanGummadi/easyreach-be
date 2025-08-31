package com.easyreach.backend.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {

    @Test
    void builderAndGetters() {
        OffsetDateTime now = OffsetDateTime.now();
        RefreshToken token = RefreshToken.builder()
                .jti("id")
                .userId("user")
                .issuedAt(now)
                .expiresAt(now.plusHours(1))
                .isSynced(true)
                .build();

        assertThat(token.getJti()).isEqualTo("id");
        assertThat(token.getExpiresAt()).isAfter(token.getIssuedAt());
        assertThat(token.getIsSynced()).isTrue();
    }
}
