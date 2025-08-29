package com.easyreach.vehicleentries.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleEntryDto {
    private String entryId;
    private String companyId;
    private String vehicleNumber;
    private String referredBy;
    private String paytype;
    private Double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
