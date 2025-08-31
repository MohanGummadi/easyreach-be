package com.easyreach.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncResponseDto {
    private Integer companies;
    private Integer dailyExpenses;
    private Integer dieselUsages;
    private Integer equipmentUsages;
    private Integer expenseMasters;
    private Integer internalVehicles;
    private Integer payers;
    private Integer payerSettlements;
    private Integer vehicleEntries;
    private Integer vehicleTypes;
}

