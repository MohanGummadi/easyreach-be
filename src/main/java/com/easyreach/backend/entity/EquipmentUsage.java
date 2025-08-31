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
@Table(name = "equipment_usage")
public class EquipmentUsage {
  @Id
  @Column(name = "equipmentUsageId", length = 20, nullable = false)
  private String equipmentUsageId;
  @Column(name = "equipment_name", nullable = false)
  private String equipmentName;
  @Column(name = "equipment_type", nullable = false)
  private String equipmentType;
  @Column(name = "start_km", nullable = false)
  private BigDecimal startKm;
  @Column(name = "end_km", nullable = false)
  private BigDecimal endKm;
  @Column(name = "start_time", nullable = false)
  private OffsetDateTime startTime;
  @Column(name = "end_time", nullable = false)
  private OffsetDateTime endTime;
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
