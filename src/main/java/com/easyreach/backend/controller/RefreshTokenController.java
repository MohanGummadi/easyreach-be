package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import com.easyreach.backend.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refresh-token")
@RequiredArgsConstructor
@Tag(name="RefreshToken")
public class RefreshTokenController {
    private final RefreshTokenService service;

    @PostMapping
    @Operation(summary = "Create RefreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> create(@Valid @RequestBody RefreshTokenRequestDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update RefreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> update(@PathVariable String id, @Valid @RequestBody RefreshTokenRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete RefreshToken")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get RefreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> get(@PathVariable String id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "List RefreshToken")
    public ResponseEntity<ApiResponse<Page<RefreshTokenResponseDto>>> list(Pageable pageable){
        return ResponseEntity.ok(service.list(pageable));
    }
}
