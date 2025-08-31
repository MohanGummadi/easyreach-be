package com.easyreach.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.math.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_entries")
public class VehicleEntry {
  @Id
  @EqualsAndHashCode.Include
  @Column(name = "entryId", length = 20, nullable = false)
  private String entryId;
  @Column(name = "company_uuid", nullable = false)
  private String companyUuid;
  @Column(name = "payer_id", nullable = false)
  private String payerId;
  @Column(name = "vehicle_number", nullable = false)
  private String vehicleNumber;
  @Column(name = "vehicle_type", nullable = false)
  private String vehicleType;
  @Column(name = "from_address", nullable = false)
  private String fromAddress;
  @Column(name = "to_address", nullable = false)
  private String toAddress;
  @Column(name = "driver_name", nullable = false)
  private String driverName;
  @Column(name = "driver_contact_no", nullable = false)
  private String driverContactNo;
  @Column(name = "commission", nullable = false)
  private BigDecimal commission;
  @Column(name = "beta", nullable = false)
  private BigDecimal beta;
  @Column(name = "referred_by")
  private String referredBy;
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;
  @Column(name = "paytype", nullable = false)
  private String paytype;
  @Column(name = "entry_date", nullable = false)
  private LocalDate entryDate;
  @Column(name = "entry_time", nullable = false)
  private OffsetDateTime entryTime;
  @Column(name = "exit_time")
  private OffsetDateTime exitTime;
  @Column(name = "notes")
  private String notes;
  @Column(name = "payment_received_by")
  private String paymentReceivedBy;
  @Column(name = "paid_amount", nullable = false)
  private BigDecimal paidAmount;
  @Column(name = "pending_amt", nullable = false)
  private BigDecimal pendingAmt;
  @Column(name = "is_settled", nullable = false)
  private Boolean isSettled;
  @Column(name = "settlement_type")
  private String settlementType;
  @Column(name = "settlement_date")
  private OffsetDateTime settlementDate;
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

  @Column(name = "deleted", nullable = false)
  private boolean deleted;

  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "change_id")
  private Long changeId;
}
