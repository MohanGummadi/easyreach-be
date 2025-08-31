package com.easyreach.backend.dto.companies;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {
  private String uuid;
  private String companyCode;
  private String companyName;
  private String companyContactNo;
  private String companyCoordinates;
  private String companyLocation;
  private LocalDate companyRegistrationDate;
  private String ownerName;
  private String ownerMobileNo;
  private String ownerEmailAddress;
  private LocalDate ownerDob;
  private Boolean isActive;
  private Boolean isSynced;
  private String createdBy;
  private OffsetDateTime createdAt;
  private String updatedBy;
  private OffsetDateTime updatedAt;
}
