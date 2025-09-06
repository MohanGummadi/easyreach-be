package com.easyreach.backend.auth.validation;

import com.easyreach.backend.auth.dto.LoginRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailOrMobileRequiredValidatorTest {

    private final EmailOrMobileRequiredValidator validator = new EmailOrMobileRequiredValidator();

    @Test
    void nullValueIsValid() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void bothEmailAndMobileMissingIsInvalid() {
        LoginRequest req = new LoginRequest();
        req.setPassword("pass");
        assertFalse(validator.isValid(req, null));
    }

    @Test
    void emailOnlyIsValid() {
        LoginRequest req = new LoginRequest();
        req.setEmail("user@example.com");
        req.setPassword("pass");
        assertTrue(validator.isValid(req, null));
    }

    @Test
    void mobileOnlyIsValid() {
        LoginRequest req = new LoginRequest();
        req.setMobileNo("+12345678901");
        req.setPassword("pass");
        assertTrue(validator.isValid(req, null));
    }

    @Test
    void blankEmailAndMobileAreTreatedAsMissing() {
        LoginRequest req = new LoginRequest();
        req.setEmail(" ");
        req.setMobileNo("   ");
        req.setPassword("pass");
        assertFalse(validator.isValid(req, null));
    }

    @Test
    void bothEmailAndMobilePresentIsValid() {
        LoginRequest req = new LoginRequest();
        req.setEmail("user@example.com");
        req.setMobileNo("+12345678901");
        req.setPassword("pass");
        assertTrue(validator.isValid(req, null));
    }
}

