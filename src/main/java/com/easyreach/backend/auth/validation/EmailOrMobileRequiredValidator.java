package com.easyreach.backend.auth.validation;

import com.easyreach.backend.auth.dto.LoginRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailOrMobileRequiredValidator implements ConstraintValidator<EmailOrMobileRequired, LoginRequest> {

    @Override
    public boolean isValid(LoginRequest value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean emailPresent = value.getEmail() != null && !value.getEmail().isBlank();
        boolean mobilePresent = value.getMobileNo() != null && !value.getMobileNo().isBlank();
        return emailPresent || mobilePresent;
    }
}

