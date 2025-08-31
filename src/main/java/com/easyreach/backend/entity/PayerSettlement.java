package com.easyreach.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payer_settlements")
public class PayerSettlement {
  @Id
  @Column(name = "settlementId", length = 20, nullable = false)
  private String settlementId;
  @Column(name = "payer_id", nullable = false)
  private String payerId;
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;
  @Column(name = "date", nullable = false)
  private OffsetDateTime date;
  @Column(name = "company_uuid", nullable = false)
  private String companyUuid;
  @Column(name = "is_synced", nullable = false)
  private Boolean isSynced;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;
  @Column(name = "updated_by")
  private String updatedBy;
  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;
}
