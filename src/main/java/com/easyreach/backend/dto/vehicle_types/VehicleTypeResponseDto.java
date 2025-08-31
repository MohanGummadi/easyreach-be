package com.easyreach.backend.dto.vehicle_types;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeResponseDto {
  private String id;
  private String vehicleType;
  private String type;
  private String companyUuid;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;
}
