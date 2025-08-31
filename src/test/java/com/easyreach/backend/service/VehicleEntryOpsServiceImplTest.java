package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.mapper.VehicleEntryMapper;
import com.easyreach.backend.repository.VehicleEntryRepository;
import com.easyreach.backend.service.impl.VehicleEntryOpsServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleEntryOpsServiceImplTest {

    @Mock
    private VehicleEntryRepository repository;
    @Mock
    private VehicleEntryMapper mapper;

    @InjectMocks
    private VehicleEntryOpsServiceImpl service;

    private VehicleEntry entity;
    private VehicleEntryResponseDto response;

    @BeforeEach
    void init() {
        entity = new VehicleEntry();
        entity.setEntryId("1");
        entity.setPaidAmount(BigDecimal.ZERO);
        entity.setAmount(new BigDecimal("100"));
        entity.setPendingAmt(new BigDecimal("100"));
        entity.setIsSettled(false);
        entity.setPaytype("CASH");
        response = new VehicleEntryResponseDto();
    }

    @Test
    void addPayment_updatesFields() {
        when(repository.findById("1")).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<VehicleEntryResponseDto> res = service.addPayment("1", new BigDecimal("50"), "user", OffsetDateTime.now());
        assertTrue(res.isSuccess());
        assertEquals(new BigDecimal("50"), entity.getPaidAmount());
        assertEquals(new BigDecimal("50"), entity.getPendingAmt());
        verify(repository).save(entity);
    }

    @Test
    void markExit_setsExitTime() {
        OffsetDateTime when = OffsetDateTime.now();
        when(repository.findById("1")).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<VehicleEntryResponseDto> res = service.markExit("1", when);
        assertTrue(res.isSuccess());
        assertEquals(when, entity.getExitTime());
        verify(repository).save(entity);
    }

    @Test
    void addPayment_notFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.addPayment("1", BigDecimal.ONE, "u", null));
    }
}

