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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Sync")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
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
        log.info("Sync request received: {}", request);
        try {
            SyncResponseDto response = new SyncResponseDto();
            if (request.getCompanies() != null) {
                response.setCompanies(companyService.bulkSync(request.getCompanies()));
            }
            if (request.getDailyExpenses() != null) {
                response.setDailyExpenses(dailyExpenseService.bulkSync(request.getDailyExpenses()));
            }
            if (request.getDieselUsages() != null) {
                response.setDieselUsages(dieselUsageService.bulkSync(request.getDieselUsages()));
            }
            if (request.getEquipmentUsages() != null) {
                response.setEquipmentUsages(equipmentUsageService.bulkSync(request.getEquipmentUsages()));
            }
            if (request.getExpenseMasters() != null) {
                response.setExpenseMasters(expenseMasterService.bulkSync(request.getExpenseMasters()));
            }
            if (request.getInternalVehicles() != null) {
                response.setInternalVehicles(internalVehicleService.bulkSync(request.getInternalVehicles()));
            }
            if (request.getPayers() != null) {
                response.setPayers(payerService.bulkSync(request.getPayers()));
            }
            if (request.getPayerSettlements() != null) {
                response.setPayerSettlements(payerSettlementService.bulkSync(request.getPayerSettlements()));
            }
            if (request.getVehicleEntries() != null) {
                response.setVehicleEntries(vehicleEntryService.bulkSync(request.getVehicleEntries()));
            }
            if (request.getVehicleTypes() != null) {
                response.setVehicleTypes(vehicleTypeService.bulkSync(request.getVehicleTypes()));
            }
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            log.error("Error during sync", e);
            throw e;
        }
    }

    @GetMapping("/sync/download")
    @Operation(summary = "Download changed entities", description = "Returns entity deltas since a cursor")
    public ResponseEntity<ApiResponse<Map<String, Object>>> downloadChanges(
            @RequestParam(required = false) String sinceCursor,
            @RequestParam(required = false) List<String> entities,
            @RequestParam(required = false) Integer limit) {
        log.info("Download changes since cursor {} for entities {} limit {}", sinceCursor, entities, limit);
        try {
            Map<String, Object> result = syncDownloadService.downloadChanges(sinceCursor, entities, limit);
            return ResponseEntity.ok(ApiResponse.success(result));
        } catch (Exception e) {
            log.error("Error downloading changes since cursor {}", sinceCursor, e);
            throw e;
        }
    }
}

