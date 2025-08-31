package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.dto.internal_vehicles.InternalVehicleResponseDto;
import com.easyreach.backend.service.InternalVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal-vehicles")
@RequiredArgsConstructor
@Tag(name="InternalVehicle")
public class InternalVehicleController {
    private final InternalVehicleService service;

    @PostMapping
    @Operation(summary = "Create InternalVehicle")
    public ResponseEntity<ApiResponse<InternalVehicleResponseDto>> create(@Valid @RequestBody InternalVehicleRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update InternalVehicle")
    public ResponseEntity<ApiResponse<InternalVehicleResponseDto>> update(@PathVariable String id, @Valid @RequestBody InternalVehicleRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete InternalVehicle")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get InternalVehicle")
    public ResponseEntity<ApiResponse<InternalVehicleResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List InternalVehicle")
    public ResponseEntity<ApiResponse<Page<InternalVehicleResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}
