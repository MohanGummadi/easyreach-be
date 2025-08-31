package com.easyreach.backend.service;

import com.easyreach.backend.dto.internal_vehicles.InternalVehicleRequestDto;
import com.easyreach.backend.entity.InternalVehicle;
import com.easyreach.backend.mapper.InternalVehicleMapper;
import com.easyreach.backend.repository.InternalVehicleRepository;
import com.easyreach.backend.service.impl.InternalVehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InternalVehicleServiceImplTest {

    @Mock
    private InternalVehicleRepository repository;
    @Mock
    private InternalVehicleMapper mapper;

    private InternalVehicleServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new InternalVehicleServiceImpl(repository, mapper);
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        InternalVehicleRequestDto d1 = new InternalVehicleRequestDto();
        d1.setVehicleId("iv1");
        InternalVehicleRequestDto d1dup = new InternalVehicleRequestDto();
        d1dup.setVehicleId("iv1");
        InternalVehicleRequestDto d2 = new InternalVehicleRequestDto();
        d2.setVehicleId("iv2");

        InternalVehicle existing = new InternalVehicle();
        existing.setVehicleId("iv1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findAllById(any())).thenReturn(List.of(existing));

        InternalVehicle newEntity = new InternalVehicle();
        newEntity.setVehicleId("iv2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<InternalVehicle>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<InternalVehicle> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, InternalVehicle> map = saved.stream().collect(java.util.stream.Collectors.toMap(InternalVehicle::getVehicleId, p -> p));

        InternalVehicle savedExisting = map.get("iv1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        InternalVehicle savedNew = map.get("iv2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

