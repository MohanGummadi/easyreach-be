package com.easyreach.backend.auth.controller;

import com.easyreach.backend.auth.service.AuthService;
import com.easyreach.backend.auth.dto.AuthResponse;
import com.easyreach.backend.auth.dto.LoginRequest;
import com.easyreach.backend.auth.dto.RefreshRequest;
import com.easyreach.backend.auth.dto.RegisterRequest;
import com.easyreach.backend.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserDto request) {
        log.info("Register request for email {}", request.getEmail());
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (Exception e) {
            log.error("Error registering user with email {}", request.getEmail(), e);
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for email {} and company {}", request.getEmail(), request.getCompanyUuid());
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            log.error("Error logging in user {}", request.getEmail(), e);
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        log.info("Refresh token request for token {}", request.getRefreshToken());
        try {
            return ResponseEntity.ok(authService.refresh(request));
        } catch (Exception e) {
            log.error("Error refreshing token {}", request.getRefreshToken(), e);
            throw e;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshRequest request) {
        log.info("Logout request for token {}", request.getRefreshToken());
        try {
            authService.logout(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error logging out token {}", request.getRefreshToken(), e);
            throw e;
        }
    }
}
