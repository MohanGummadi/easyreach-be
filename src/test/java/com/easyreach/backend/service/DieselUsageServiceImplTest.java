package com.easyreach.backend.service;

import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.entity.DieselUsage;
import com.easyreach.backend.mapper.DieselUsageMapper;
import com.easyreach.backend.repository.DieselUsageRepository;
import com.easyreach.backend.service.impl.DieselUsageServiceImpl;
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
class DieselUsageServiceImplTest {

    @Mock
    private DieselUsageRepository repository;
    @Mock
    private DieselUsageMapper mapper;

    private DieselUsageServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new DieselUsageServiceImpl(repository, mapper);
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        DieselUsageRequestDto d1 = new DieselUsageRequestDto();
        d1.setDieselUsageId("d1");
        DieselUsageRequestDto d1dup = new DieselUsageRequestDto();
        d1dup.setDieselUsageId("d1");
        DieselUsageRequestDto d2 = new DieselUsageRequestDto();
        d2.setDieselUsageId("d2");

        DieselUsage existing = new DieselUsage();
        existing.setDieselUsageId("d1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findAllById(any())).thenReturn(List.of(existing));

        DieselUsage newEntity = new DieselUsage();
        newEntity.setDieselUsageId("d2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<DieselUsage>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<DieselUsage> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, DieselUsage> map = saved.stream().collect(java.util.stream.Collectors.toMap(DieselUsage::getDieselUsageId, p -> p));

        DieselUsage savedExisting = map.get("d1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        DieselUsage savedNew = map.get("d2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

