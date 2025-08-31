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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-expenses")
@RequiredArgsConstructor
@Tag(name="DailyExpense")
@SecurityRequirement(name = "bearerAuth")
public class DailyExpenseController {
    private final DailyExpenseService service;

    @PostMapping
    @Operation(summary = "Create DailyExpense")
    public ResponseEntity<ApiResponse<DailyExpenseResponseDto>> create(@Valid @RequestBody DailyExpenseRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update DailyExpense")
    public ResponseEntity<ApiResponse<DailyExpenseResponseDto>> update(@PathVariable String id, @Valid @RequestBody DailyExpenseRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete DailyExpense")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get DailyExpense")
    public ResponseEntity<ApiResponse<DailyExpenseResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List DailyExpense")
    public ResponseEntity<ApiResponse<Page<DailyExpenseResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}
