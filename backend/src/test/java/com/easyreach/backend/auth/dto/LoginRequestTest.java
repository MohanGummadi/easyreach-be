package com.easyreach.backend.auth.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validationFailsWithoutEmailAndMobile() {
        LoginRequest req = new LoginRequest();
        req.setPassword("pass");
        assertThat(validator.validate(req)).hasSizeGreaterThan(0);
    }

    @Test
    void validationPassesWithEmail() {
        LoginRequest req = new LoginRequest();
        req.setEmail("user@example.com");
        req.setPassword("pass");
        assertThat(validator.validate(req)).isEmpty();
    }

    @Test
    void validationPassesWithMobile() {
        LoginRequest req = new LoginRequest();
        req.setMobileNo("1234567890");
        req.setPassword("pass");
        assertThat(validator.validate(req)).isEmpty();
    }

    @Test
    void gettersAndSetters() {
        LoginRequest req = new LoginRequest();
        req.setEmail("user@example.com");
        req.setMobileNo("1234567890");
        req.setPassword("pass");
        assertThat(req.getEmail()).isEqualTo("user@example.com");
        assertThat(req.getMobileNo()).isEqualTo("1234567890");
        assertThat(req.getPassword()).isEqualTo("pass");
    }
}

