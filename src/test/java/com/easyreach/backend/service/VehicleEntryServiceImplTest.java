package com.easyreach.backend.service;

import com.easyreach.backend.security.CompanyContext;

import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.entity.VehicleEntry;
import com.easyreach.backend.mapper.VehicleEntryMapper;
import com.easyreach.backend.repository.VehicleEntryRepository;
import com.easyreach.backend.service.impl.VehicleEntryServiceImpl;
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
class VehicleEntryServiceImplTest {

    @Mock
    private VehicleEntryRepository repository;
    @Mock
    private VehicleEntryMapper mapper;

    private VehicleEntryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new VehicleEntryServiceImpl(repository, mapper);
        CompanyContext.setCompanyId("test");
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        VehicleEntryRequestDto d1 = new VehicleEntryRequestDto();
        d1.setEntryId("v1");
        VehicleEntryRequestDto d1dup = new VehicleEntryRequestDto();
        d1dup.setEntryId("v1");
        VehicleEntryRequestDto d2 = new VehicleEntryRequestDto();
        d2.setEntryId("v2");

        VehicleEntry existing = new VehicleEntry();
        existing.setEntryId("v1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findByEntryIdInAndCompanyUuid(any(), anyString())).thenReturn(List.of(existing));

        VehicleEntry newEntity = new VehicleEntry();
        newEntity.setEntryId("v2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<VehicleEntry>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<VehicleEntry> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, VehicleEntry> map = saved.stream().collect(java.util.stream.Collectors.toMap(VehicleEntry::getEntryId, p -> p));

        VehicleEntry savedExisting = map.get("v1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        VehicleEntry savedNew = map.get("v2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

