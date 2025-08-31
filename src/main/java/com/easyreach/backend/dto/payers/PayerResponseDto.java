package com.easyreach.backend.dto.payers;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayerResponseDto {
  private String payerId;
  private String payerName;
  private String mobileNo;
  private String payerAddress;
  private LocalDate registrationDate;
  private Integer creditLimit;
  private String companyUuid;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;
  private OffsetDateTime deletedAt;
}
