package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.SyncRequestDto;
import com.easyreach.backend.dto.SyncResponseDto;
import com.easyreach.backend.service.*;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Sync")
@SecurityRequirement(name = "bearerAuth")
public class SyncController {
    private final CompanyService companyService;
    private final DailyExpenseService dailyExpenseService;
    private final DieselUsageService dieselUsageService;
    private final EquipmentUsageService equipmentUsageService;
    private final ExpenseMasterService expenseMasterService;
    private final InternalVehicleService internalVehicleService;
    private final PayerService payerService;
    private final PayerSettlementService payerSettlementService;
    private final VehicleEntryService vehicleEntryService;
    private final VehicleTypeService vehicleTypeService;
    private final SyncDownloadService syncDownloadService;

    @PostMapping("/sync")
    @Operation(summary = "Bulk synchronize entities", description = "Accepts lists of entity DTOs and persists them with isSynced=true")
    public ResponseEntity<ApiResponse<SyncResponseDto>> sync(@Valid @RequestBody SyncRequestDto request) {
        SyncResponseDto response = new SyncResponseDto();
        if (request.getCompanies() != null && !request.getCompanies().isEmpty()) {
            response.setCompanies(companyService.bulkSync(request.getCompanies()));
        }
        if (request.getDailyExpenses() != null && !request.getDailyExpenses().isEmpty()) {
            response.setDailyExpenses(dailyExpenseService.bulkSync(request.getDailyExpenses()));
        }
        if (request.getDieselUsages() != null && !request.getDieselUsages().isEmpty()) {
            response.setDieselUsages(dieselUsageService.bulkSync(request.getDieselUsages()));
        }
        if (request.getEquipmentUsages() != null && !request.getEquipmentUsages().isEmpty()) {
            response.setEquipmentUsages(equipmentUsageService.bulkSync(request.getEquipmentUsages()));
        }
        if (request.getExpenseMasters() != null && !request.getExpenseMasters().isEmpty()) {
            response.setExpenseMasters(expenseMasterService.bulkSync(request.getExpenseMasters()));
        }
        if (request.getInternalVehicles() != null && !request.getInternalVehicles().isEmpty()) {
            response.setInternalVehicles(internalVehicleService.bulkSync(request.getInternalVehicles()));
        }
        if (request.getPayers() != null && !request.getPayers().isEmpty()) {
            response.setPayers(payerService.bulkSync(request.getPayers()));
        }
        if (request.getPayerSettlements() != null && !request.getPayerSettlements().isEmpty()) {
            response.setPayerSettlements(payerSettlementService.bulkSync(request.getPayerSettlements()));
        }
        if (request.getVehicleEntries() != null && !request.getVehicleEntries().isEmpty()) {
            response.setVehicleEntries(vehicleEntryService.bulkSync(request.getVehicleEntries()));
        }
        if (request.getVehicleTypes() != null && !request.getVehicleTypes().isEmpty()) {
            response.setVehicleTypes(vehicleTypeService.bulkSync(request.getVehicleTypes()));
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/sync/download")
    @Operation(summary = "Download changed entities", description = "Returns entity deltas since a cursor")
    public ResponseEntity<ApiResponse<Map<String, Object>>> downloadChanges(
            @RequestParam(required = false) String sinceCursor,
            @RequestParam(required = false) List<String> entities,
            @RequestParam(required = false) Integer limit) {
        Map<String, Object> result = syncDownloadService.downloadChanges(sinceCursor, entities, limit);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

