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
public class VehicleTypeDto {
  private String id;
  private String vehicleType;
  private String type;
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
