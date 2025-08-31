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
@Table(name = "payers")
public class Payer {
  @Id
  @Column(name = "payerId", length = 20, nullable = false)
  private String payerId;
  @Column(name = "payer_name", nullable = false)
  private String payerName;
  @Column(name = "mobile_no", nullable = false)
  private String mobileNo;
  @Column(name = "payer_address")
  private String payerAddress;
  @Column(name = "registration_date")
  private LocalDate registrationDate;
  @Column(name = "credit_limit")
  private Integer creditLimit;
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
  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;
}
