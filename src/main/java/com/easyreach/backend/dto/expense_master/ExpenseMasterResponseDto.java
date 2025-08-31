package com.easyreach.backend.dto.expense_master;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseMasterResponseDto {
  private String id;
  private String expenseName;
  private String companyUuid;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;
}
