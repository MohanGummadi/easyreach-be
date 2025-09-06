package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RefreshTokenService {
    ApiResponse<RefreshTokenResponseDto> create(RefreshTokenRequestDto dto);
    ApiResponse<RefreshTokenResponseDto> update(String id, RefreshTokenRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<RefreshTokenResponseDto> get(String id);
    ApiResponse<Page<RefreshTokenResponseDto>> list(Pageable pageable);
}
