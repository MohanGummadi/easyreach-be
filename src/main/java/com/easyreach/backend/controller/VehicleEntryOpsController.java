package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.service.VehicleEntryOpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/vehicle-entries-ops")
@RequiredArgsConstructor
@Tag(name = "VehicleEntryOps")
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
        return ResponseEntity.ok(service.addPayment(entryId, req.getAmount(), req.getReceivedBy(), req.getWhen()));
    }

    @PostMapping("/{entryId}/exit")
    @Operation(summary = "Mark vehicle exit time")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> exit(@PathVariable String entryId, @RequestParam(required = false) OffsetDateTime when) {
        return ResponseEntity.ok(service.markExit(entryId, when));
    }
}
