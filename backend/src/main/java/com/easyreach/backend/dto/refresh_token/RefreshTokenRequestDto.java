package com.easyreach.backend.dto.refresh_token;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequestDto {
  @NotBlank
  private String jti;
  @NotBlank
  private String userId;
  @NotNull
  private OffsetDateTime issuedAt;
  @NotNull
  private OffsetDateTime expiresAt;

  private OffsetDateTime revokedAt;

  private String rotatedFromJti;

  private Boolean isSynced;

  private String createdBy;

  private OffsetDateTime createdAt;

  private String updatedBy;

  private OffsetDateTime updatedAt;
}
