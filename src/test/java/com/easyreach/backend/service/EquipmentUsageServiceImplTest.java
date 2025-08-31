package com.easyreach.backend.service;

import com.easyreach.backend.security.CompanyContext;

import com.easyreach.backend.dto.equipment_usage.EquipmentUsageRequestDto;
import com.easyreach.backend.entity.EquipmentUsage;
import com.easyreach.backend.mapper.EquipmentUsageMapper;
import com.easyreach.backend.repository.EquipmentUsageRepository;
import com.easyreach.backend.service.impl.EquipmentUsageServiceImpl;
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
class EquipmentUsageServiceImplTest {

    @Mock
    private EquipmentUsageRepository repository;
    @Mock
    private EquipmentUsageMapper mapper;

    private EquipmentUsageServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new EquipmentUsageServiceImpl(repository, mapper);
        CompanyContext.setCompanyId("test");
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        EquipmentUsageRequestDto d1 = new EquipmentUsageRequestDto();
        d1.setEquipmentUsageId("eq1");
        EquipmentUsageRequestDto d1dup = new EquipmentUsageRequestDto();
        d1dup.setEquipmentUsageId("eq1");
        EquipmentUsageRequestDto d2 = new EquipmentUsageRequestDto();
        d2.setEquipmentUsageId("eq2");

        EquipmentUsage existing = new EquipmentUsage();
        existing.setEquipmentUsageId("eq1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findByEquipmentUsageIdInAndCompanyUuid(any(), anyString())).thenReturn(List.of(existing));

        EquipmentUsage newEntity = new EquipmentUsage();
        newEntity.setEquipmentUsageId("eq2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<EquipmentUsage>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<EquipmentUsage> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, EquipmentUsage> map = saved.stream().collect(java.util.stream.Collectors.toMap(EquipmentUsage::getEquipmentUsageId, p -> p));

        EquipmentUsage savedExisting = map.get("eq1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        EquipmentUsage savedNew = map.get("eq2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

