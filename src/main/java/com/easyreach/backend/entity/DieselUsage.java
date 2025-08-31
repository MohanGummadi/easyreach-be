package com.easyreach.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.math.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diesel_usage")
public class DieselUsage {
  @Id
  @EqualsAndHashCode.Include
  @Column(name = "dieselUsageId", length = 20, nullable = false)
  private String dieselUsageId;
  @Column(name = "vehicle_name", nullable = false)
  private String vehicleName;
  @Column(name = "date", nullable = false)
  private OffsetDateTime date;
  @Column(name = "liters", nullable = false)
  private BigDecimal liters;
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

  @Column(name = "deleted", nullable = false)
  private boolean deleted;

  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "change_id")
  private Long changeId;
}
