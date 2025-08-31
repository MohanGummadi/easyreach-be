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
public class EquipmentUsageDto {
  private String equipmentUsageId;
  private String equipmentName;
  private String equipmentType;
  private Double startKM;
  private Double endKM;
  private Instant startTime;
  private Instant endTime;
  private Instant date;
  private String companyId;
  private Integer isSynced;
  private String createdBy;
  private Instant createdAt;
  private String updatedBy;
  private Instant updatedAt;

  private Boolean deleted;
  private Instant deletedAt;
  private Long changeId;
}
