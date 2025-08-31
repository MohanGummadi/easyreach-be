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
@Table(name = "internal_vehicles")
public class InternalVehicle {
  @Id
  @EqualsAndHashCode.Include
  @Column(name = "vehicleId", length = 20, nullable = false)
  private String vehicleId;
  @Column(name = "vehicle_name", nullable = false)
  private String vehicleName;
  @Column(name = "vehicle_type", nullable = false)
  private String vehicleType;
  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
  @Column(name = "company_uuid")
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
