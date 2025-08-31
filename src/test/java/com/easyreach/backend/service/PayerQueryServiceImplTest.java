package com.easyreach.backend.service;

import com.easyreach.backend.security.CompanyContext;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.entity.Payer;
import com.easyreach.backend.mapper.PayerMapper;
import com.easyreach.backend.repository.PayerRepository;
import com.easyreach.backend.service.impl.PayerQueryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayerQueryServiceImplTest {

    @Mock
    private PayerRepository repository;
    @Mock
    private PayerMapper mapper;

    @InjectMocks
    private PayerQueryServiceImpl service;

    private Payer entity;
    private PayerResponseDto response;

    @BeforeEach
    void init() {
        entity = new Payer();
        CompanyContext.setCompanyId("test");
        response = new PayerResponseDto();
    }

    @Test
    void searchActive_noQuery_usesRepository() {
        Page<Payer> page = new PageImpl<>(List.of(entity));
        when(repository.findByCompanyUuidAndDeletedAtIsNull(eq("c"), any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<Page<PayerResponseDto>> res = service.searchActive("c", null, Pageable.unpaged());
        assertTrue(res.isSuccess());
        verify(repository).findByCompanyUuidAndDeletedAtIsNull(eq("c"), any(Pageable.class));
    }

    @Test
    void softDelete_marksDeleted() {
        when(repository.findByPayerIdAndCompanyUuidAndDeletedIsFalse("1", "test")).thenReturn(Optional.of(entity));

        ApiResponse<Void> res = service.softDelete("1");
        assertTrue(res.isSuccess());
        assertNotNull(entity.getDeletedAt());
        verify(repository).save(entity);
    }

    @Test
    void softDelete_notFound() {
        when(repository.findByPayerIdAndCompanyUuidAndDeletedIsFalse("1", "test")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.softDelete("1"));
    }
}

