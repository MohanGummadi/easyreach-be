package com.easyreach.backend.dto.expense_master;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseMasterRequestDto {
  @NotBlank
  private String id;
  @NotBlank
  private String expenseName;
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
