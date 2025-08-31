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
public class PayerDto {
  private String payerId;
  private String payerName;
  private String mobileNo;
  private String payerAddress;
  private LocalDate registrationDate;
  private Integer creditLimit;
  private String companyId;
  private Integer isSynced;
  private String createdBy;
  private Instant createdAt;
  private String updatedBy;
  private Instant updatedAt;

  private Boolean deleted;
  private Instant deletedAt;
  private Long changeId;
}
