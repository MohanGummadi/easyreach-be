package com.easyreach.backend.auth.service;

import com.easyreach.backend.auth.dto.AuthResponse;
import com.easyreach.backend.auth.dto.LoginRequest;
import com.easyreach.backend.auth.dto.RefreshRequest;
import com.easyreach.backend.auth.entity.RefreshToken;
import com.easyreach.backend.auth.entity.Role;
import com.easyreach.backend.dto.UserDto;
import com.easyreach.backend.entity.User;
import com.easyreach.backend.repository.RefreshTokenRepository;
import com.easyreach.backend.repository.UserRepository;
import com.easyreach.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    /**
     * Registers a user using the new String-id User entity.
     * If the mobile app supplies IDs, we use them; otherwise we fall back to generated values.
     */
    @Transactional
    public AuthResponse register(UserDto request) {
        // Prefer IDs coming from the client (Android), fall back to generated when absent
        String userId = request.getId() != null ? request.getId() : UUID.randomUUID().toString();
        String employeeId = request.getEmployeeId() != null ? request.getEmployeeId() : UUID.randomUUID().toString();

        User user = User.builder()
                .id(userId)
                .employeeId(employeeId)
                .email(request.getEmail())
                .mobileNo(request.getMobileNo())
                .name(request.getName())
                .companyUuid(request.getCompanyId())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER.name()) // if your User entity keeps role as String; change to Role.USER if it's an enum field
                .isActive(true)
                .build();

        userRepository.save(user);
        return createTokens(user, null);
    }

    /**
     * Authenticates via Spring Security, then loads our User entity by email.
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // After successful auth, load our domain User by email (principal may not be our entity)
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getEmail()));

        return createTokens(user, null);
    }

    /**
     * Rotates refresh tokens: revoke old, issue new pair.
     */
    @Transactional
    public AuthResponse refresh(RefreshRequest request) {
        String token = request.getRefreshToken();
        String jti = jwtService.extractJti(token);

        RefreshToken stored = refreshTokenRepository.findByJtiAndRevokedAtIsNull(jti)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (stored.getExpiresAt().isBefore(OffsetDateTime.now(ZoneOffset.UTC))) {
            throw new RuntimeException("Refresh token expired");
        }

        User user = userRepository.findById(stored.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + stored.getUserId()));

        // revoke the used refresh token
        stored.setRevokedAt(OffsetDateTime.now(ZoneOffset.UTC));
        refreshTokenRepository.save(stored);

        // issue fresh pair; record rotation chain
        return createTokens(user, jti);
    }

    /**
     * Soft revoke a refresh token (logout current session).
     */
    @Transactional
    public void logout(RefreshRequest request) {
        String jti = jwtService.extractJti(request.getRefreshToken());
        refreshTokenRepository.findById(jti).ifPresent(token -> {
            token.setRevokedAt(OffsetDateTime.now(ZoneOffset.UTC));
            refreshTokenRepository.save(token);
        });
    }

    /**
     * Helper: creates access + refresh token pair and persists refresh token metadata.
     */
    private AuthResponse createTokens(User user, String rotatedFrom) {
        String jti = UUID.randomUUID().toString();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user, jti);

        RefreshToken entity = RefreshToken.builder()
                .jti(jti)
                .userId(user.getId())                  // String user id
                .issuedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .expiresAt(OffsetDateTime.now(ZoneOffset.UTC).plus(7, ChronoUnit.DAYS))
                .rotatedFromJti(rotatedFrom)
                .build();

        refreshTokenRepository.save(entity);
        return new AuthResponse(accessToken, refreshToken);
    }
}
