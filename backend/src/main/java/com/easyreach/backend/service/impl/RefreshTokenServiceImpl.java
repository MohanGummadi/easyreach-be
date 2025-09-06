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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;

    @Override
    public ApiResponse<RefreshTokenResponseDto> create(RefreshTokenRequestDto dto) {
        log.debug("Entering create with dto={}", dto);
        RefreshToken entity = mapper.toEntity(dto);
        ApiResponse<RefreshTokenResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(entity)));
        log.debug("Exiting create with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<RefreshTokenResponseDto> update(String id, RefreshTokenRequestDto dto) {
        log.debug("Entering update with id={} dto={}", id, dto);
        RefreshToken e = repository.findById(id).orElseThrow(() -> {
            log.error("RefreshToken not found: {}", id);
            return new EntityNotFoundException("RefreshToken not found: " + id);
        });
        mapper.update(e, dto);
        ApiResponse<RefreshTokenResponseDto> response = ApiResponse.success(mapper.toDto(repository.save(e)));
        log.debug("Exiting update with response={}", response);
        return response;
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        log.debug("Entering delete with id={}", id);
        if (!repository.existsById(id)) {
            log.error("RefreshToken not found: {}", id);
            throw new EntityNotFoundException("RefreshToken not found: " + id);
        }
        repository.deleteById(id);
        ApiResponse<Void> response = ApiResponse.success(null);
        log.debug("Exiting delete with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<RefreshTokenResponseDto> get(String id) {
        log.debug("Entering get with id={}", id);
        RefreshToken e = repository.findById(id).orElseThrow(() -> {
            log.error("RefreshToken not found: {}", id);
            return new EntityNotFoundException("RefreshToken not found: " + id);
        });
        ApiResponse<RefreshTokenResponseDto> response = ApiResponse.success(mapper.toDto(e));
        log.debug("Exiting get with response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<RefreshTokenResponseDto>> list(Pageable pageable) {
        log.debug("Entering list with pageable={}", pageable);
        ApiResponse<Page<RefreshTokenResponseDto>> response = ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
        log.debug("Exiting list with response={}", response);
        return response;
    }
}
