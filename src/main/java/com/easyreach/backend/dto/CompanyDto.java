package com.easyreach.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
  private String id;
  private String companyId;
  private String companyName;
  private String companyContactNo;
  private String companyCoordinates;
  private String companyLocation;
  private String companyRegistrationDate;
  private String ownerName;
  private String ownerMobileNo;
  private String ownerEmailAddress;
  private String ownerDOB;
  private Integer isActive;
  private Integer isSynced;
  private String createdBy;
  private Instant createdAt;
  private String updatedBy;
  private Instant updatedAt;
}
