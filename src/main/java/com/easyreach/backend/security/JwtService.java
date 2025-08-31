package com.easyreach.backend.security;

import com.easyreach.backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret-base64:}")
    private String secretBase64;

    @Value("${jwt.access-token.ttl-minutes:15}")
    private long accessTtlMinutes;

    @Value("${jwt.refresh-token.ttl-days:7}")
    private long refreshTtlDays;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretBase64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User user) {
        log.debug("Generating access token for user={}", user.getEmail());
        return Jwts.builder()
                .setSubject(user.getEmail()) // or username field
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(accessTtlMinutes)))
                .claim("companyId", user.getCompanyUuid())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user, String jti) {
        log.debug("Generating refresh token for user={} jti={}", user.getEmail(), jti);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setId(jti)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(refreshTtlDays)))
                .claim("companyId", user.getCompanyUuid())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        log.debug("Extracting username from token");
        return extractAllClaims(token).getSubject();
    }

    public String extractJti(String token) {
        log.debug("Extracting jti from token");
        return extractAllClaims(token).getId();
    }

    public String extractCompanyId(String token) {
        log.debug("Extracting companyId from token");
        return extractAllClaims(token).get("companyId", String.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean valid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        if (!valid) {
            log.warn("Token validation failed for user={}", userDetails.getUsername());
        }
        return valid;
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Failed to parse token", e);
            throw e;
        }
    }
}
