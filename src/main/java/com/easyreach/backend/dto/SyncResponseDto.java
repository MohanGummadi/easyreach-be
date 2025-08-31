package com.easyreach.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncResponseDto {
    private int companies;
    private int dailyExpenses;
    private int dieselUsages;
    private int equipmentUsages;
    private int expenseMasters;
    private int internalVehicles;
    private int payers;
    private int payerSettlements;
    private int vehicleEntries;
    private int vehicleTypes;
}

