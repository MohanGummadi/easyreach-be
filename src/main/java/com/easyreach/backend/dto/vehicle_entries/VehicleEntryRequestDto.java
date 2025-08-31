package com.easyreach.backend.dto.vehicle_entries;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntryRequestDto {
  @NotBlank
  private String entryId;
  @NotBlank
  private String companyUuid;
  @NotBlank
  private String payerId;
  @NotBlank
  private String vehicleNumber;
  @NotBlank
  private String vehicleType;
  @NotBlank
  private String fromAddress;
  @NotBlank
  private String toAddress;
  @NotBlank
  private String driverName;
  @NotBlank
  private String driverContactNo;
  @NotNull
  private BigDecimal commission;
  @NotNull
  private BigDecimal beta;

  private String referredBy;
  @NotNull
  private BigDecimal amount;
  @NotBlank
  private String paytype;
  @NotNull
  private LocalDate entryDate;
  @NotNull
  private OffsetDateTime entryTime;

  private OffsetDateTime exitTime;

  private String notes;

  private String paymentReceivedBy;
  @NotNull
  private BigDecimal paidAmount;
  @NotNull
  private BigDecimal pendingAmt;
  @NotNull
  private Boolean isSettled;

  private String settlementType;

  private OffsetDateTime settlementDate;
  @NotNull
  private Boolean isSynced;

  private String createdBy;
  @NotNull
  private OffsetDateTime createdAt;

  private String updatedBy;
  @NotNull
  private OffsetDateTime updatedAt;
}
