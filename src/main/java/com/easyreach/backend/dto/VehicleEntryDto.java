package com.easyreach.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntryDto {
  private String entryId;
  private String companyId;
  private String payerId;
  private String vehicleNumber;
  private String vehicleType;
  private String fromAddress;
  private String toAddress;
  private String driverName;
  private String driverContactNo;
  private Double commission;
  private Double beta;
  private String refferedBy;
  private Double amount;
  private String paytype;
  private LocalDate entryDate;
  private Instant entryTime;
  private Instant exitTime;
  private String notes;
  private String paymentReceivedBy;
  private Double paidAmount;
  private Double pendingAmt;
  private Integer isSettled;
  private String settlementType;
  private Instant settlementDate;
  private Integer isSynced;
  private String createdBy;
  private Instant createdAt;
  private String updatedBy;
  private Instant updatedAt;

  private Boolean deleted;
  private Instant deletedAt;
  private Long changeId;
}
