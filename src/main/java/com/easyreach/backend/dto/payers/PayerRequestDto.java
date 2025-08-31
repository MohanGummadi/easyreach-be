package com.easyreach.backend.dto.payers;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayerRequestDto {
  @NotBlank
  private String payerId;
  @NotBlank
  private String payerName;
  @NotBlank
  private String mobileNo;

  private String payerAddress;

  private LocalDate registrationDate;

  private Integer creditLimit;
  @NotBlank
  private String companyUuid;
  @NotNull
  private Boolean isSynced;

  private String createdBy;
  @NotNull
  private OffsetDateTime createdAt;

  private String updatedBy;
  @NotNull
  private OffsetDateTime updatedAt;

  private OffsetDateTime deletedAt;
}
