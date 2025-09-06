package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.service.VehicleEntryOpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/vehicle-entries-ops")
@RequiredArgsConstructor
@Tag(name = "VehicleEntryOps")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class VehicleEntryOpsController {

    private final VehicleEntryOpsService service;

    @Getter
    @Setter
    public static class AddPaymentRequest {
        @NotNull
        private BigDecimal amount;
        private String receivedBy;
        private OffsetDateTime when;
    }

    @PostMapping("/{entryId}/payment")
    @Operation(summary = "Add a payment towards a vehicle entry")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> addPayment(@PathVariable String entryId, @RequestBody AddPaymentRequest req) {
        log.info("Adding payment to vehicle entry {} amount {}", entryId, req.getAmount());
        try {
            return ResponseEntity.ok(service.addPayment(entryId, req.getAmount(), req.getReceivedBy(), req.getWhen()));
        } catch (Exception e) {
            log.error("Error adding payment to vehicle entry {}", entryId, e);
            throw e;
        }
    }

    @PostMapping("/{entryId}/exit")
    @Operation(summary = "Mark vehicle exit time")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> exit(@PathVariable String entryId, @RequestParam(required = false) OffsetDateTime when) {
        log.info("Marking exit for vehicle entry {} at {}", entryId, when);
        try {
            return ResponseEntity.ok(service.markExit(entryId, when));
        } catch (Exception e) {
            log.error("Error marking exit for vehicle entry {}", entryId, e);
            throw e;
        }
    }
}
