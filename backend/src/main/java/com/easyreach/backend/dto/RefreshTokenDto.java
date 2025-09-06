package com.easyreach.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
  private String jti;
  private String userId;
  private Instant issuedAt;
  private Instant expiresAt;
  private Instant revokedAt;
  private String rotatedFromJti;
}
