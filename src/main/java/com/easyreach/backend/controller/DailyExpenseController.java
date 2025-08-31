package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseResponseDto;
import com.easyreach.backend.service.DailyExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-expenses")
@RequiredArgsConstructor
@Tag(name="DailyExpense")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class DailyExpenseController {
    private final DailyExpenseService service;

    @PostMapping
    @Operation(summary = "Create DailyExpense")
    public ResponseEntity<ApiResponse<DailyExpenseResponseDto>> create(@Valid @RequestBody DailyExpenseRequestDto dto){
        log.info("Create DailyExpense request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating DailyExpense with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update DailyExpense")
    public ResponseEntity<ApiResponse<DailyExpenseResponseDto>> update(@PathVariable String id, @Valid @RequestBody DailyExpenseRequestDto dto){
        log.info("Update DailyExpense id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating DailyExpense id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete DailyExpense")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete DailyExpense id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting DailyExpense id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get DailyExpense")
    public ResponseEntity<ApiResponse<DailyExpenseResponseDto>> get(@PathVariable String id){
        log.info("Get DailyExpense id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting DailyExpense id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List DailyExpense")
    public ResponseEntity<ApiResponse<Page<DailyExpenseResponseDto>>> list(Pageable pageable){
        log.info("List DailyExpense with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing DailyExpense", e);
            throw e;
        }
    }
}
