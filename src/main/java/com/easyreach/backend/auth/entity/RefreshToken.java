package com.easyreach.backend.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
  @Id
  @Column(name = "jti", length = 100, nullable = false)
  private String jti;
  @Column(name = "user_id", nullable = false)
  private String userId;
  @Column(name = "issued_at", nullable = false)
  private OffsetDateTime issuedAt;
  @Column(name = "expires_at", nullable = false)
  private OffsetDateTime expiresAt;
  @Column(name = "revoked_at")
  private OffsetDateTime revokedAt;
  @Column(name = "rotated_from_jti")
  private String rotatedFromJti;
  @Column(name = "is_synced")
  private Boolean isSynced;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_at")
  private OffsetDateTime createdAt;
  @Column(name = "updated_by")
  private String updatedBy;
  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;
}
