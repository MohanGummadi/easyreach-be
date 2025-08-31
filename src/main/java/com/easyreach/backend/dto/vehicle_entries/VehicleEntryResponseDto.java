package com.easyreach.backend.dto.vehicle_entries;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntryResponseDto {
  private String entryId;
  private String companyUuid;
  private String payerId;
  private String vehicleNumber;
  private String vehicleType;
  private String fromAddress;
  private String toAddress;
  private String driverName;
  private String driverContactNo;
  private BigDecimal commission;
  private BigDecimal beta;
  private String referredBy;
  private BigDecimal amount;
  private String paytype;
  private LocalDate entryDate;
  private OffsetDateTime entryTime;
  private OffsetDateTime exitTime;
  private String notes;
  private String paymentReceivedBy;
  private BigDecimal paidAmount;
  private BigDecimal pendingAmt;
  private Boolean isSettled;
  private String settlementType;
  private OffsetDateTime settlementDate;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;

  private Boolean deleted;
  private OffsetDateTime deletedAt;
  private Long changeId;
}
