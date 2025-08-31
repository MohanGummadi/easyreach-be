package com.easyreach.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyExpenseDto {
  private String expenseId;
  private String expenseType;
  private Double expenseAmount;
  private Instant expenseDate;
  private String expenseNote;
  private String companyId;
  private Integer isSynced;
  private String createdBy;
  private Instant createdAt;
  private String updatedBy;
  private Instant updatedAt;
}
