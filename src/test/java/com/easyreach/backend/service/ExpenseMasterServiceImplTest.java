package com.easyreach.backend.service;

import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.entity.ExpenseMaster;
import com.easyreach.backend.mapper.ExpenseMasterMapper;
import com.easyreach.backend.repository.ExpenseMasterRepository;
import com.easyreach.backend.service.impl.ExpenseMasterServiceImpl;
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
class ExpenseMasterServiceImplTest {

    @Mock
    private ExpenseMasterRepository repository;
    @Mock
    private ExpenseMasterMapper mapper;

    private ExpenseMasterServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ExpenseMasterServiceImpl(repository, mapper);
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        ExpenseMasterRequestDto d1 = new ExpenseMasterRequestDto();
        d1.setId("em1");
        ExpenseMasterRequestDto d1dup = new ExpenseMasterRequestDto();
        d1dup.setId("em1");
        ExpenseMasterRequestDto d2 = new ExpenseMasterRequestDto();
        d2.setId("em2");

        ExpenseMaster existing = new ExpenseMaster();
        existing.setId("em1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findAllById(any())).thenReturn(List.of(existing));

        ExpenseMaster newEntity = new ExpenseMaster();
        newEntity.setId("em2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<ExpenseMaster>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<ExpenseMaster> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, ExpenseMaster> map = saved.stream().collect(java.util.stream.Collectors.toMap(ExpenseMaster::getId, p -> p));

        ExpenseMaster savedExisting = map.get("em1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        ExpenseMaster savedNew = map.get("em2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

