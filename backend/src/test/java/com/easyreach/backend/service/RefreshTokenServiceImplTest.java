package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import com.easyreach.backend.auth.entity.RefreshToken;
import com.easyreach.backend.mapper.RefreshTokenMapper;
import com.easyreach.backend.repository.RefreshTokenRepository;
import com.easyreach.backend.service.impl.RefreshTokenServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository repository;
    @Mock
    private RefreshTokenMapper mapper;

    @InjectMocks
    private RefreshTokenServiceImpl service;

    private RefreshToken entity;
    private RefreshTokenRequestDto request;
    private RefreshTokenResponseDto response;

    @BeforeEach
    void init() {
        entity = new RefreshToken();
        request = new RefreshTokenRequestDto();
        response = new RefreshTokenResponseDto();
    }

    @Test
    void create_savesEntity() {
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<RefreshTokenResponseDto> res = service.create(request);

        assertTrue(res.isSuccess());
        verify(repository).save(entity);
    }

    @Test
    void get_notFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.get("1"));
    }

    @Test
    void get_found() {
        when(repository.findById("1")).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(response);
        ApiResponse<RefreshTokenResponseDto> res = service.get("1");
        assertTrue(res.isSuccess());
        verify(repository).findById("1");
    }

    @Test
    void list_returnsPage() {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<?> res = service.list(Pageable.unpaged());
        assertTrue(res.isSuccess());
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    void update_updatesEntity() {
        when(repository.findById("1")).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<RefreshTokenResponseDto> res = service.update("1", request);

        assertTrue(res.isSuccess());
        verify(mapper).update(entity, request);
        verify(repository).save(entity);
    }

    @Test
    void update_notFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update("1", request));
    }

    @Test
    void delete_deletesEntity() {
        when(repository.existsById("1")).thenReturn(true);
        ApiResponse<Void> res = service.delete("1");
        assertTrue(res.isSuccess());
        verify(repository).deleteById("1");
    }

    @Test
    void delete_notFound() {
        when(repository.existsById("1")).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.delete("1"));
    }
}

