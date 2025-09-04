package com.easyreach.backend.auth.dto;

import com.easyreach.backend.auth.validation.EmailOrMobileRequired;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@EmailOrMobileRequired
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {
    @Email
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String mobileNo;

    @NotBlank
    private String password;
}

