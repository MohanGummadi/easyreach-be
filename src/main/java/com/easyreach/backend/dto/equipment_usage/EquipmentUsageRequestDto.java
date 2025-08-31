package com.easyreach.backend.dto.equipment_usage;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentUsageRequestDto {
  @NotBlank
  private String equipmentUsageId;
  @NotBlank
  private String equipmentName;
  @NotBlank
  private String equipmentType;
  @NotNull
  private BigDecimal startKm;
  @NotNull
  private BigDecimal endKm;
  @NotNull
  private OffsetDateTime startTime;
  @NotNull
  private OffsetDateTime endTime;
  @NotNull
  private OffsetDateTime date;
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
