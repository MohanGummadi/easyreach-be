package com.easyreach.backend.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailOrMobileRequiredValidator.class)
public @interface EmailOrMobileRequired {
    String message() default "Either email or mobile number must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

