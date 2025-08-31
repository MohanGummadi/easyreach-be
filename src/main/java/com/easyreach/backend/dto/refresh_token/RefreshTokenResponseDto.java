package com.easyreach.backend.dto.refresh_token;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponseDto {
  private String jti;
  private String userId;
  private OffsetDateTime issuedAt;
  private OffsetDateTime expiresAt;
  private OffsetDateTime revokedAt;
  private String rotatedFromJti;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;
}
