package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import com.easyreach.backend.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refresh-token")
@RequiredArgsConstructor
@Tag(name="RefreshToken")
@Slf4j
public class RefreshTokenController {
    private final RefreshTokenService service;

    @PostMapping
    @Operation(summary = "Create RefreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> create(@Valid @RequestBody RefreshTokenRequestDto dto){
        log.info("Create RefreshToken request: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            log.error("Error creating RefreshToken with payload {}", dto, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update RefreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> update(@PathVariable String id, @Valid @RequestBody RefreshTokenRequestDto dto){
        log.info("Update RefreshToken id {}", id);
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            log.error("Error updating RefreshToken id {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete RefreshToken")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        log.info("Delete RefreshToken id {}", id);
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            log.error("Error deleting RefreshToken id {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get RefreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> get(@PathVariable String id){
        log.info("Get RefreshToken id {}", id);
        try {
            return ResponseEntity.ok(service.get(id));
        } catch (Exception e) {
            log.error("Error getting RefreshToken id {}", id, e);
            throw e;
        }
    }

    @GetMapping
    @Operation(summary = "List RefreshToken")
    public ResponseEntity<ApiResponse<Page<RefreshTokenResponseDto>>> list(Pageable pageable){
        log.info("List RefreshToken with pageable {}", pageable);
        try {
            return ResponseEntity.ok(service.list(pageable));
        } catch (Exception e) {
            log.error("Error listing RefreshTokens", e);
            throw e;
        }
    }
}
