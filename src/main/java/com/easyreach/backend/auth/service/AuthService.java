package com.easyreach.backend.auth.service;

import com.easyreach.backend.auth.entity.RefreshToken;
import com.easyreach.backend.auth.entity.Role;
import com.easyreach.backend.auth.entity.User;
import com.easyreach.backend.auth.repository.RefreshTokenRepository;
import com.easyreach.backend.auth.repository.UserRepository;
import com.easyreach.backend.auth.dto.AuthResponse;
import com.easyreach.backend.auth.dto.LoginRequest;
import com.easyreach.backend.auth.dto.RefreshRequest;
import com.easyreach.backend.auth.dto.RegisterRequest;
import com.easyreach.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .uuid(UUID.randomUUID().toString())
                .employeeId(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .isActive(1)
                .build();
        userRepository.save(user);
        return createTokens(user, null);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = (User) authentication.getPrincipal();
        return createTokens(user, null);
    }

    public AuthResponse refresh(RefreshRequest request) {
        String token = request.getRefreshToken();
        String jti = jwtService.extractJti(token);
        RefreshToken stored = refreshTokenRepository.findByJtiAndRevokedAtIsNull(jti)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (stored.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }
        User user = userRepository.findById(stored.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        stored.setRevokedAt(Instant.now());
        refreshTokenRepository.save(stored);
        return createTokens(user, jti);
    }

    public void logout(RefreshRequest request) {
        String jti = jwtService.extractJti(request.getRefreshToken());
        refreshTokenRepository.findById(jti).ifPresent(token -> {
            token.setRevokedAt(Instant.now());
            refreshTokenRepository.save(token);
        });
    }

    private AuthResponse createTokens(User user, String rotatedFrom) {
        String jti = UUID.randomUUID().toString();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user, jti);
        RefreshToken entity = RefreshToken.builder()
                .jti(jti)
                .userId(user.getId())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .rotatedFromJti(rotatedFrom)
                .build();
        refreshTokenRepository.save(entity);
        return new AuthResponse(accessToken, refreshToken);
    }
}
