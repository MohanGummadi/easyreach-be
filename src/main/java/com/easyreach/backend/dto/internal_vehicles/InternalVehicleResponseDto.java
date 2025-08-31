package com.easyreach.backend.dto.internal_vehicles;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalVehicleResponseDto {
  private String vehicleId;
  private String vehicleName;
  private String vehicleType;
  private Boolean isActive;
  private String companyUuid;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;

  private Boolean deleted;
  private OffsetDateTime deletedAt;
  private Long changeId;
}
