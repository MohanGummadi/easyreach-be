package com.easyreach.backend.dto;

import java.util.List;

import jakarta.validation.Valid;

import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;

import lombok.Data;

@Data
public class SyncRequestDto {
    @Valid
    private List<CompanyRequestDto> companies;
    @Valid
    private List<DailyExpenseRequestDto> dailyExpenses;
    @Valid
    private List<DieselUsageRequestDto> dieselUsages;
    @Valid
    private List<EquipmentUsageRequestDto> equipmentUsages;
    @Valid
    private List<ExpenseMasterRequestDto> expenseMasters;
    @Valid
    private List<InternalVehicleRequestDto> internalVehicles;
    @Valid
    private List<PayerRequestDto> payers;
    @Valid
    private List<PayerSettlementRequestDto> payerSettlements;
    @Valid
    private List<VehicleEntryRequestDto> vehicleEntries;
    @Valid
    private List<VehicleTypeRequestDto> vehicleTypes;
}

