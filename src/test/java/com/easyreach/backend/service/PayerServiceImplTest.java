package com.easyreach.backend.service;

import com.easyreach.backend.security.CompanyContext;

import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.entity.Payer;
import com.easyreach.backend.mapper.PayerMapper;
import com.easyreach.backend.repository.PayerRepository;
import com.easyreach.backend.service.impl.PayerServiceImpl;
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
class PayerServiceImplTest {

    @Mock
    private PayerRepository repository;
    @Mock
    private PayerMapper mapper;

    private PayerServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PayerServiceImpl(repository, mapper);
        CompanyContext.setCompanyId("test");
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        PayerRequestDto d1 = new PayerRequestDto();
        d1.setPayerId("p1");
        PayerRequestDto d1dup = new PayerRequestDto();
        d1dup.setPayerId("p1");
        PayerRequestDto d2 = new PayerRequestDto();
        d2.setPayerId("p2");

        Payer existing = new Payer();
        existing.setPayerId("p1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findByPayerIdInAndCompanyUuid(any(), anyString())).thenReturn(List.of(existing));

        Payer newEntity = new Payer();
        newEntity.setPayerId("p2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<Payer>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<Payer> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, Payer> map = saved.stream().collect(java.util.stream.Collectors.toMap(Payer::getPayerId, p -> p));

        Payer savedExisting = map.get("p1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        Payer savedNew = map.get("p2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

