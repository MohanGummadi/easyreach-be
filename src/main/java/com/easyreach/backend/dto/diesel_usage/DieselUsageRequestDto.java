package com.easyreach.backend.dto.diesel_usage;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DieselUsageRequestDto {
  @NotBlank
  private String dieselUsageId;
  @NotBlank
  private String vehicleName;
  @NotNull
  private OffsetDateTime date;
  @NotNull
  private BigDecimal liters;
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
}
