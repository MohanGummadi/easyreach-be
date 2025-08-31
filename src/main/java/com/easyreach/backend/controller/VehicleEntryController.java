package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.service.VehicleEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle-entries")
@RequiredArgsConstructor
@Tag(name="VehicleEntry")
public class VehicleEntryController {
    private final VehicleEntryService service;

    @PostMapping
    @Operation(summary = "Create VehicleEntry")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> create(@Valid @RequestBody VehicleEntryRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update VehicleEntry")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> update(@PathVariable String id, @Valid @RequestBody VehicleEntryRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete VehicleEntry")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get VehicleEntry")
    public ResponseEntity<ApiResponse<VehicleEntryResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List VehicleEntry")
    public ResponseEntity<ApiResponse<Page<VehicleEntryResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}
