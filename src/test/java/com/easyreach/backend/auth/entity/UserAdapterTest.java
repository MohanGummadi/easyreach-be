package com.easyreach.backend.auth.entity;

import com.easyreach.backend.entity.User;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserAdapterTest {

    @Test
    void wrapsUserDetailsCorrectly() {
        OffsetDateTime now = OffsetDateTime.now();
        User domainUser = User.builder()
                .id("1")
                .employeeId("E1")
                .email("user@example.com")
                .password("pass")
                .role("ADMIN")
                .isActive(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        UserAdapter adapter = new UserAdapter(domainUser);

        assertThat(adapter.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_ADMIN");
        assertThat(adapter.getUsername()).isEqualTo("user@example.com");
        assertThat(adapter.isEnabled()).isTrue();
        assertThat(adapter.getDomainUser()).isSameAs(domainUser);
    }
}
