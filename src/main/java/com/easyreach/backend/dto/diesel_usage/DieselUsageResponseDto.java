package com.easyreach.backend.dto.diesel_usage;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DieselUsageResponseDto {
  private String dieselUsageId;
  private String vehicleName;
  private OffsetDateTime date;
  private BigDecimal liters;
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
