package com.easyreach.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {
    @Mock
    private JwtService jwtService;
    @Mock
    private UserDetailsService userDetailsService;
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        SecurityContextHolder.clearContext();
    }

    @Test
    void missingHeader_callsChain() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        SecurityContext context = mock(SecurityContext.class);
        when(request.getHeader("Authorization")).thenReturn(null);
        when(context.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(context);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(context, never()).setAuthentication(any());
    }

    @Test
    void validToken_setsAuthentication() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);
        SecurityContext context = mock(SecurityContext.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUsername("token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("token", userDetails)).thenReturn(true);
        when(jwtService.extractCompanyId("token")).thenReturn("company1");
        when(context.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(context);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(context).setAuthentication(any(Authentication.class));
    }

    @Test
    void validToken_withMobileSubject_setsAuthentication() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);
        SecurityContext context = mock(SecurityContext.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUsername("token")).thenReturn("9999999999");
        when(userDetailsService.loadUserByUsername("9999999999")).thenReturn(userDetails);
        when(jwtService.isTokenValid("token", userDetails)).thenReturn(true);
        when(jwtService.extractCompanyId("token")).thenReturn("company1");
        when(context.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(context);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(context).setAuthentication(any(Authentication.class));
    }

    @Test
    void invalidToken_returnsUnauthorized() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        SecurityContext context = mock(SecurityContext.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer bad");
        when(jwtService.extractUsername("bad")).thenThrow(new RuntimeException("invalid"));
        when(context.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(context);

        filter.doFilterInternal(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        verify(chain, never()).doFilter(request, response);
        verify(context, never()).setAuthentication(any());
    }

    @Test
    void missingCompanyId_returnsUnauthorized() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);
        SecurityContext context = mock(SecurityContext.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUsername("token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("token", userDetails)).thenReturn(true);
        when(jwtService.extractCompanyId("token")).thenReturn(null);
        when(context.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(context);

        filter.doFilterInternal(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing company ID");
        verify(chain, never()).doFilter(request, response);
        verify(context, never()).setAuthentication(any());
    }
}
