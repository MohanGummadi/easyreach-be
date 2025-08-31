package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import com.easyreach.backend.auth.entity.RefreshToken;
import com.easyreach.backend.mapper.RefreshTokenMapper;
import com.easyreach.backend.repository.RefreshTokenRepository;
import com.easyreach.backend.service.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;

    @Override
    public ApiResponse<RefreshTokenResponseDto> create(RefreshTokenRequestDto dto) {
        RefreshToken entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<RefreshTokenResponseDto> update(String id, RefreshTokenRequestDto dto) {
        RefreshToken e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("RefreshToken not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("RefreshToken not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<RefreshTokenResponseDto> get(String id) {
        RefreshToken e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("RefreshToken not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<RefreshTokenResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }
}
