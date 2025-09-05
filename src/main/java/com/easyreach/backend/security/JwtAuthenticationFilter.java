package com.easyreach.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // will be CustomUserDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No Bearer token found on request {}", request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        log.debug("Processing JWT for request {}", request.getRequestURI());

        try {
            String identifier = jwtService.extractUsername(token);
            log.debug("Extracted subject {} from token", identifier);

            if (identifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = userDetailsService.loadUserByUsername(identifier);
                if (jwtService.isTokenValid(token, userDetails)) {
                    String companyId = jwtService.extractCompanyId(token);
                    if (companyId == null || companyId.isBlank()) {
                        log.warn("Token missing company ID for user {}", identifier);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing company ID");
                        return;
                    }
                    CompanyContext.setCompanyId(companyId);
                    var auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.debug("Authentication successful for user {} company {}", identifier, companyId);
                } else {
                    log.warn("Token validation failed for user {}", identifier);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT processing failed", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        } finally {
            CompanyContext.clear();
        }
    }
}
