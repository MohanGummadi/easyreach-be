package com.easyreach.backend.service;

import com.easyreach.backend.dto.vehicle_types.VehicleTypeRequestDto;
import com.easyreach.backend.entity.VehicleType;
import com.easyreach.backend.mapper.VehicleTypeMapper;
import com.easyreach.backend.repository.VehicleTypeRepository;
import com.easyreach.backend.service.impl.VehicleTypeServiceImpl;
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
class VehicleTypeServiceImplTest {

    @Mock
    private VehicleTypeRepository repository;
    @Mock
    private VehicleTypeMapper mapper;

    private VehicleTypeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new VehicleTypeServiceImpl(repository, mapper);
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        VehicleTypeRequestDto d1 = new VehicleTypeRequestDto();
        d1.setId("t1");
        VehicleTypeRequestDto d1dup = new VehicleTypeRequestDto();
        d1dup.setId("t1");
        VehicleTypeRequestDto d2 = new VehicleTypeRequestDto();
        d2.setId("t2");

        VehicleType existing = new VehicleType();
        existing.setId("t1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findAllById(any())).thenReturn(List.of(existing));

        VehicleType newEntity = new VehicleType();
        newEntity.setId("t2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<VehicleType>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<VehicleType> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, VehicleType> map = saved.stream().collect(java.util.stream.Collectors.toMap(VehicleType::getId, p -> p));

        VehicleType savedExisting = map.get("t1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        VehicleType savedNew = map.get("t2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

