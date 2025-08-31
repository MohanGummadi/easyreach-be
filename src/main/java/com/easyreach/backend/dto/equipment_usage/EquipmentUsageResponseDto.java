package com.easyreach.backend.dto.equipment_usage;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentUsageResponseDto {
  private String equipmentUsageId;
  private String equipmentName;
  private String equipmentType;
  private BigDecimal startKm;
  private BigDecimal endKm;
  private OffsetDateTime startTime;
  private OffsetDateTime endTime;
  private OffsetDateTime date;
  private String companyUuid;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;
}
