package com.easyreach.backend.dto.daily_expenses;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyExpenseRequestDto {
  @NotBlank
  private String expenseId;
  @NotBlank
  private String expenseType;
  @NotNull
  private BigDecimal expenseAmount;
  @NotNull
  private OffsetDateTime expenseDate;

  private String expenseNote;
  @NotNull
  private Boolean isPaid;

  private String paidBy;

  private String paidTo;

  private OffsetDateTime paidDate;
  @NotBlank
  private String companyUuid;

  private String userId;
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
