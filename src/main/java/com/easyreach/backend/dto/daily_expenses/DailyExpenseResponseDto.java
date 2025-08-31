package com.easyreach.backend.dto.daily_expenses;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyExpenseResponseDto {
  private String expenseId;
  private String expenseType;
  private BigDecimal expenseAmount;
  private OffsetDateTime expenseDate;
  private String expenseNote;
  private Boolean isPaid;
  private String paidBy;
  private String paidTo;
  private OffsetDateTime paidDate;
  private String companyUuid;
  private String userId;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;

  private Boolean deleted;
  private OffsetDateTime deletedAt;
  private Long changeId;
}
