package com.easyreach.backend.controllers;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.CompanyDto;
import com.easyreach.backend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService service;

    @Autowired
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @PostMapping("/companies")
    public ResponseEntity<ApiResponse<CompanyDto>> create(@RequestBody CompanyDto dto) {
        try {
            CompanyDto created = service.create(dto);
            return ResponseEntity.ok(ApiResponse.success(created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(List.of(e.getMessage())));
        }
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> get(@PathVariable String id) {
        CompanyDto dto = service.get(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @GetMapping("/companies")
    public ResponseEntity<ApiResponse<Page<CompanyDto>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyDto> result = service.list(pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> update(@PathVariable String id, @RequestBody CompanyDto dto) {
        try {
            CompanyDto updated = service.update(id, dto);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ApiResponse.success(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(List.of(e.getMessage())));
        }
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
