package com.easyreach.backend.dto.vehicle_types;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeRequestDto {
  @NotBlank
  private String id;
  @NotBlank
  private String vehicleType;
  @NotBlank
  private String type;
  @NotBlank
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
