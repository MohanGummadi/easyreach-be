package com.easyreach.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "daily_expenses")
public class DailyExpense {
  @Id
  @Column(name = "expenseId", length = 20, nullable = false)
  private String expenseId;
  @Column(name = "expense_type", nullable = false)
  private String expenseType;
  @Column(name = "expense_amount", nullable = false)
  private BigDecimal expenseAmount;
  @Column(name = "expense_date", nullable = false)
  private OffsetDateTime expenseDate;
  @Column(name = "expense_note")
  private String expenseNote;
  @Column(name = "is_paid", nullable = false)
  private Boolean isPaid;
  @Column(name = "paid_by")
  private String paidBy;
  @Column(name = "paid_to")
  private String paidTo;
  @Column(name = "paid_date")
  private OffsetDateTime paidDate;
  @Column(name = "company_uuid", nullable = false)
  private String companyUuid;
  @Column(name = "user_id")
  private String userId;
  @Column(name = "is_synced", nullable = false)
  private Boolean isSynced;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;
  @Column(name = "updated_by")
  private String updatedBy;
  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;
}
