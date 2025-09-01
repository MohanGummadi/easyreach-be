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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@Slf4j
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
        log.debug("Entering register with request={}", request);
        // Reject duplicate mobile numbers before persisting
        if (request.getMobileNo() != null && userRepository.existsByMobileNo(request.getMobileNo())) {
            log.warn("Attempt to register with duplicate mobile number: {}", request.getMobileNo());
            throw new IllegalArgumentException("Mobile number already registered");
        }

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
                .role(request.getRole()) // if your User entity keeps role as String; change to Role.USER if it's an enum field
                .isActive(true)
                .build();

        try {
            userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            log.error("Constraint violation during registration for mobile no: {}", request.getMobileNo(), e);
            throw new IllegalArgumentException("User information violates constraints", e);
        }
        AuthResponse response = createTokens(user, null);
        log.debug("Exiting register for userId={}", user.getId());
        return response;
    }

    /**
     * Authenticates via Spring Security, then loads our User entity by email.
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String identifier = request.getEmail() != null ? request.getEmail() : request.getMobileNo();
        log.debug("Entering login with identifier={}", identifier);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(identifier, request.getPassword())
        );

        User user;
        if (request.getEmail() != null) {
            user = userRepository.findByEmailIgnoreCase(request.getEmail())
                    .orElseThrow(() -> {
                        log.warn("User not found during login: {}", request.getEmail());
                        return new UsernameNotFoundException("User not found: " + request.getEmail());
                    });
        } else {
            user = userRepository.findByMobileNo(request.getMobileNo())
                    .orElseThrow(() -> {
                        log.warn("User not found during login: {}", request.getMobileNo());
                        return new UsernameNotFoundException("User not found: " + request.getMobileNo());
                    });
        }

        AuthResponse response = createTokens(user, null);
        log.debug("Exiting login for userId={}", user.getId());
        return response;
    }

    /**
     * Rotates refresh tokens: revoke old, issue new pair.
     */
    @Transactional
    public AuthResponse refresh(RefreshRequest request) {
        String token = request.getRefreshToken();
        String jti = jwtService.extractJti(token);
        log.debug("Entering refresh for jti={}", jti);

        RefreshToken stored = refreshTokenRepository.findByJtiAndRevokedAtIsNull(jti)
                .orElseThrow(() -> {
                    log.error("Invalid refresh token jti={}", jti);
                    return new RuntimeException("Invalid refresh token");
                });

        if (stored.getExpiresAt().isBefore(OffsetDateTime.now(ZoneOffset.UTC))) {
            log.warn("Refresh token expired jti={}", jti);
            throw new RuntimeException("Refresh token expired");
        }

        User user = userRepository.findById(stored.getUserId())
                .orElseThrow(() -> {
                    log.warn("User not found for id={} during refresh", stored.getUserId());
                    return new UsernameNotFoundException("User not found: " + stored.getUserId());
                });

        // revoke the used refresh token
        stored.setRevokedAt(OffsetDateTime.now(ZoneOffset.UTC));
        refreshTokenRepository.save(stored);

        // issue fresh pair; record rotation chain
        AuthResponse response = createTokens(user, jti);
        log.debug("Exiting refresh for jti={}", jti);
        return response;
    }

    /**
     * Soft revoke a refresh token (logout current session).
     */
    @Transactional
    public void logout(RefreshRequest request) {
        log.debug("Entering logout with token={}", request.getRefreshToken());
        String jti = jwtService.extractJti(request.getRefreshToken());
        refreshTokenRepository.findById(jti).ifPresent(token -> {
            token.setRevokedAt(OffsetDateTime.now(ZoneOffset.UTC));
            refreshTokenRepository.save(token);
        });
        log.debug("Exiting logout for jti={}", jti);
    }

    /**
     * Helper: creates access + refresh token pair and persists refresh token metadata.
     */
    private AuthResponse createTokens(User user, String rotatedFrom) {
        log.debug("Entering createTokens for userId={} rotatedFrom={}", user.getId(), rotatedFrom);
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
        AuthResponse response = new AuthResponse(accessToken, refreshToken);
        log.debug("Exiting createTokens with jti={}", jti);
        return response;
    }
}
