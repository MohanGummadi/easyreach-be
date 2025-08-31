package com.easyreach.backend.auth.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validationFailsForBlankFields() {
        LoginRequest req = new LoginRequest();
        assertThat(validator.validate(req)).hasSizeGreaterThan(0);
    }

    @Test
    void gettersAndSetters() {
        LoginRequest req = new LoginRequest();
        req.setEmail("user@example.com");
        req.setPassword("pass");
        req.setCompanyUuid("c1");
        assertThat(req.getEmail()).isEqualTo("user@example.com");
        assertThat(req.getPassword()).isEqualTo("pass");
        assertThat(req.getCompanyUuid()).isEqualTo("c1");
        assertThat(validator.validate(req)).isEmpty();
    }
}
