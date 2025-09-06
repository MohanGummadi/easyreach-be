package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface VehicleEntryOpsService {
    ApiResponse<VehicleEntryResponseDto> addPayment(String entryId, BigDecimal amount, String receivedBy, OffsetDateTime when);
    ApiResponse<VehicleEntryResponseDto> markExit(String entryId, OffsetDateTime when);
}
