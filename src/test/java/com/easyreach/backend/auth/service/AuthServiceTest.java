package com.easyreach.backend.auth.service;

import com.easyreach.backend.auth.dto.AuthResponse;
import com.easyreach.backend.auth.dto.LoginRequest;
import com.easyreach.backend.auth.dto.RefreshRequest;
import com.easyreach.backend.dto.UserDto;
import com.easyreach.backend.auth.entity.RefreshToken;
import com.easyreach.backend.entity.User;
import com.easyreach.backend.repository.RefreshTokenRepository;
import com.easyreach.backend.repository.UserRepository;
import com.easyreach.backend.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id("u1").email("e@e.com").password("pass").companyUuid("c1").build();
    }

    @Test
    void register_savesUserAndToken() {
        UserDto dto = new UserDto();
        dto.setEmail("e@e.com");
        dto.setPassword("p");
        dto.setCompanyId("c1");
        dto.setMobileNo("1234567890");
        when(userRepository.existsByMobileNo("1234567890")).thenReturn(false);
        when(passwordEncoder.encode("p")).thenReturn("enc");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateAccessToken(any())).thenReturn("a");
        when(jwtService.generateRefreshToken(any(), anyString())).thenReturn("r");

        AuthResponse resp = authService.register(dto);
        assertNotNull(resp.getAccessToken());
        verify(userRepository).save(any(User.class));
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void register_duplicateMobileNo_throwsException() {
        UserDto dto = new UserDto();
        dto.setMobileNo("1234567890");
        when(userRepository.existsByMobileNo("1234567890")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> authService.register(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_authenticatesAndReturnsTokens() {
        LoginRequest req = new LoginRequest();
        req.setEmail("e@e.com");
        req.setPassword("p");
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(userRepository.findByEmailIgnoreCase("e@e.com")).thenReturn(Optional.of(user));
        when(jwtService.generateAccessToken(user)).thenReturn("a");
        when(jwtService.generateRefreshToken(eq(user), anyString())).thenReturn("r");

        AuthResponse resp = authService.login(req);
        assertEquals("a", resp.getAccessToken());
    }

    @Test
    void login_withMobile_authenticatesAndReturnsTokens() {
        LoginRequest req = new LoginRequest();
        req.setMobileNo("1234567890");
        req.setPassword("p");
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(userRepository.findByMobileNo("1234567890")).thenReturn(Optional.of(user));
        when(jwtService.generateAccessToken(user)).thenReturn("a");
        when(jwtService.generateRefreshToken(eq(user), anyString())).thenReturn("r");

        AuthResponse resp = authService.login(req);
        assertEquals("a", resp.getAccessToken());
        verify(userRepository).findByMobileNo("1234567890");
    }

    @Test
    void refresh_rotatesToken_persistsRevocationAndNewToken_inOrder() {
        // Arrange
        RefreshRequest req = new RefreshRequest();
        req.setRefreshToken("token");

        RefreshToken stored = RefreshToken.builder()
                .jti("jti-old")
                .userId("u1")
                .expiresAt(OffsetDateTime.now(ZoneOffset.UTC).plusDays(1))
                .build();

        when(jwtService.extractJti("token")).thenReturn("jti-old");
        when(refreshTokenRepository.findByJtiAndRevokedAtIsNull("jti-old"))
                .thenReturn(Optional.of(stored));
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(jwtService.generateAccessToken(user)).thenReturn("access-new");
        when(jwtService.generateRefreshToken(eq(user), anyString())).thenReturn("refresh-new");

        // Act
        AuthResponse resp = authService.refresh(req);

        // Assert response
        assertNotNull(resp);
        assertEquals("access-new", resp.getAccessToken());
        assertEquals("refresh-new", resp.getRefreshToken());

        // Assert order + semantics of the two saves (don't use a prior plain verify on save)
        InOrder inOrder = inOrder(refreshTokenRepository);

        // 1) First save: revoke the existing token
        inOrder.verify(refreshTokenRepository).save(argThat(t ->
                t != null
                        && "jti-old".equals(t.getJti())
                        && "u1".equals(t.getUserId())
                        && t.getRevokedAt() != null // must be marked revoked
        ));

        // 2) Second save: persist the new token (fresh JTI, not revoked)
        inOrder.verify(refreshTokenRepository).save(argThat(t ->
                t != null
                        && "u1".equals(t.getUserId())
                        && t.getRevokedAt() == null
                        && t.getJti() != null
                        && !"jti-old".equals(t.getJti())
        ));

        inOrder.verifyNoMoreInteractions();
    }


    @Test
    void logout_revokesToken() {
        RefreshRequest req = new RefreshRequest();
        req.setRefreshToken("token");
        when(jwtService.extractJti("token")).thenReturn("jti");
        RefreshToken token = new RefreshToken();
        when(refreshTokenRepository.findById("jti")).thenReturn(Optional.of(token));

        authService.logout(req);
        verify(refreshTokenRepository).save(token);
    }
}
