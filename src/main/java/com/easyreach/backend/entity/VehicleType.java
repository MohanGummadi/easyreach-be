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
@Table(name = "vehicle_types")
public class VehicleType {
  @Id
  @Column(name = "id", length = 20, nullable = false)
  private String id;
  @Column(name = "vehicle_type", nullable = false)
  private String vehicleType;
  @Column(name = "type", nullable = false)
  private String type;
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
