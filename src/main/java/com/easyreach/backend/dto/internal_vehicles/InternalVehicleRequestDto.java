package com.easyreach.backend.dto.internal_vehicles;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalVehicleRequestDto {
  @NotBlank
  private String vehicleId;
  @NotBlank
  private String vehicleName;
  @NotBlank
  private String vehicleType;
  @NotNull
  private Boolean isActive;

  private String companyUuid;
  @NotNull
  private Boolean isSynced;

  private String createdBy;
  @NotNull
  private OffsetDateTime createdAt;

  private String updatedBy;
  @NotNull
  private OffsetDateTime updatedAt;

  @NotNull
  private Boolean deleted;

  private OffsetDateTime deletedAt;

  private Long changeId;
}
