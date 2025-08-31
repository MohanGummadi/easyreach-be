package com.easyreach.backend.dto.companies;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDto {
  @NotBlank
  private String uuid;
  @NotBlank
  private String companyCode;
  @NotBlank
  private String companyName;
  @NotBlank
  private String companyContactNo;

  private String companyCoordinates;
  @NotBlank
  private String companyLocation;
  @NotNull
  private LocalDate companyRegistrationDate;
  @NotBlank
  private String ownerName;
  @NotBlank
  private String ownerMobileNo;
  @NotBlank
  private String ownerEmailAddress;
  @NotNull
  private LocalDate ownerDob;
  @NotNull
  private Boolean isActive;
  @NotNull
  private Boolean isSynced;

  private String createdBy;
  @NotNull
  private OffsetDateTime createdAt;

  private String updatedBy;
  @NotNull
  private OffsetDateTime updatedAt;
}
