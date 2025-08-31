package com.easyreach.backend.service;

import com.easyreach.backend.security.CompanyContext;

import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.entity.PayerSettlement;
import com.easyreach.backend.mapper.PayerSettlementMapper;
import com.easyreach.backend.repository.PayerSettlementRepository;
import com.easyreach.backend.service.impl.PayerSettlementServiceImpl;
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
class PayerSettlementServiceImplTest {

    @Mock
    private PayerSettlementRepository repository;
    @Mock
    private PayerSettlementMapper mapper;

    private PayerSettlementServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PayerSettlementServiceImpl(repository, mapper);
        CompanyContext.setCompanyId("test");
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        PayerSettlementRequestDto d1 = new PayerSettlementRequestDto();
        d1.setSettlementId("s1");
        PayerSettlementRequestDto d1dup = new PayerSettlementRequestDto();
        d1dup.setSettlementId("s1");
        PayerSettlementRequestDto d2 = new PayerSettlementRequestDto();
        d2.setSettlementId("s2");

        PayerSettlement existing = new PayerSettlement();
        existing.setSettlementId("s1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findBySettlementIdInAndCompanyUuid(any(), anyString())).thenReturn(List.of(existing));

        PayerSettlement newEntity = new PayerSettlement();
        newEntity.setSettlementId("s2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<PayerSettlement>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<PayerSettlement> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, PayerSettlement> map = saved.stream().collect(java.util.stream.Collectors.toMap(PayerSettlement::getSettlementId, p -> p));

        PayerSettlement savedExisting = map.get("s1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        PayerSettlement savedNew = map.get("s2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

