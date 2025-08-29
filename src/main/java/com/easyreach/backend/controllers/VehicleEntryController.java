package com.easyreach.backend.controllers;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.VehicleEntryDto;
import com.easyreach.backend.services.VehicleEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class VehicleEntryController {

    private final VehicleEntryService service;

    @Autowired
    public VehicleEntryController(VehicleEntryService service) {
        this.service = service;
    }

    @PostMapping("/vehicle-entries")
    public ResponseEntity<ApiResponse<VehicleEntryDto>> create(@RequestBody VehicleEntryDto dto) {
        try {
            VehicleEntryDto created = service.create(dto);
            return ResponseEntity.ok(ApiResponse.success(created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(List.of(e.getMessage())));
        }
    }

    @GetMapping("/vehicle-entries/{id}")
    public ResponseEntity<ApiResponse<VehicleEntryDto>> get(@PathVariable String id) {
        VehicleEntryDto dto = service.get(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @GetMapping("/vehicle-entries")
    public ResponseEntity<ApiResponse<Page<VehicleEntryDto>>> list(
            @RequestParam String companyId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleEntryDto> result = service.list(companyId, start, end, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PutMapping("/vehicle-entries/{id}")
    public ResponseEntity<ApiResponse<VehicleEntryDto>> update(@PathVariable String id, @RequestBody VehicleEntryDto dto) {
        try {
            VehicleEntryDto updated = service.update(id, dto);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ApiResponse.success(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(List.of(e.getMessage())));
        }
    }

    @DeleteMapping("/vehicle-entries/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/sync/vehicle-entries")
    public ResponseEntity<ApiResponse<List<VehicleEntryDto>>> sync(@RequestBody List<VehicleEntryDto> dtos) {
        List<VehicleEntryDto> results = dtos.stream().map(service::upsert).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/sync/changes/vehicle-entries")
    public ResponseEntity<ApiResponse<Page<VehicleEntryDto>>> changes(
            @RequestParam String companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleEntryDto> result = service.list(companyId, null, null, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
