package com.easyreach.backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CompanyDto {
    private String uuid;
    private String companyId;
    private String companyName;
    private String companyContactNo;
    private String companyCoordinates;
    private String companyLocation;
    private LocalDate companyRegistrationDate;
    private String ownerName;
    private String ownerMobileNo;
    private String ownerEmailAddress;
    private LocalDate ownerDOB;
    private Integer isActive = 1;
    private Integer isSynced = 0;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
