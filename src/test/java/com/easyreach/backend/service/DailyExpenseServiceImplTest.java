package com.easyreach.backend.service;

import com.easyreach.backend.security.CompanyContext;

import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.entity.DailyExpense;
import com.easyreach.backend.mapper.DailyExpenseMapper;
import com.easyreach.backend.repository.DailyExpenseRepository;
import com.easyreach.backend.service.impl.DailyExpenseServiceImpl;
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
class DailyExpenseServiceImplTest {

    @Mock
    private DailyExpenseRepository repository;
    @Mock
    private DailyExpenseMapper mapper;

    private DailyExpenseServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new DailyExpenseServiceImpl(repository, mapper);
        CompanyContext.setCompanyId("test");
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        DailyExpenseRequestDto d1 = new DailyExpenseRequestDto();
        d1.setExpenseId("e1");
        DailyExpenseRequestDto d1dup = new DailyExpenseRequestDto();
        d1dup.setExpenseId("e1");
        DailyExpenseRequestDto d2 = new DailyExpenseRequestDto();
        d2.setExpenseId("e2");

        DailyExpense existing = new DailyExpense();
        existing.setExpenseId("e1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findByExpenseIdInAndCompanyUuid(any(), anyString())).thenReturn(List.of(existing));

        DailyExpense newEntity = new DailyExpense();
        newEntity.setExpenseId("e2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<DailyExpense>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<DailyExpense> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, DailyExpense> map = saved.stream().collect(java.util.stream.Collectors.toMap(DailyExpense::getExpenseId, p -> p));

        DailyExpense savedExisting = map.get("e1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        DailyExpense savedNew = map.get("e2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

