package com.easyreach.backend.service;

import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.entity.Company;
import com.easyreach.backend.mapper.CompanyMapper;
import com.easyreach.backend.repository.CompanyRepository;
import com.easyreach.backend.service.impl.CompanyServiceImpl;
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
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository repository;
    @Mock
    private CompanyMapper mapper;

    private CompanyServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CompanyServiceImpl(repository, mapper);
    }

    @Test
    void bulkSync_deduplicates_merges_and_sets_timestamps() {
        CompanyRequestDto d1 = new CompanyRequestDto();
        d1.setUuid("u1");
        CompanyRequestDto d1dup = new CompanyRequestDto();
        d1dup.setUuid("u1");
        CompanyRequestDto d2 = new CompanyRequestDto();
        d2.setUuid("u2");

        Company existing = new Company();
        existing.setUuid("u1");
        OffsetDateTime old = OffsetDateTime.now().minusDays(1);
        existing.setCreatedAt(old);
        existing.setUpdatedAt(old);

        when(repository.findAllById(any())).thenReturn(List.of(existing));

        Company newEntity = new Company();
        newEntity.setUuid("u2");
        when(mapper.toEntity(d2)).thenReturn(newEntity);

        ArgumentCaptor<List<Company>> saveCaptor = ArgumentCaptor.forClass(List.class);

        int count = service.bulkSync(Arrays.asList(d1, d1dup, d2));

        assertEquals(2, count);
        verify(mapper).merge(d1dup, existing);
        verify(mapper).toEntity(d2);

        verify(repository).saveAll(saveCaptor.capture());
        List<Company> saved = saveCaptor.getValue();
        assertEquals(2, saved.size());

        Map<String, Company> map = saved.stream().collect(java.util.stream.Collectors.toMap(Company::getUuid, c -> c));

        Company savedExisting = map.get("u1");
        assertNotNull(savedExisting);
        assertEquals(old, savedExisting.getCreatedAt());
        assertTrue(savedExisting.getUpdatedAt().isAfter(old));
        assertTrue(savedExisting.getIsSynced());

        Company savedNew = map.get("u2");
        assertNotNull(savedNew);
        assertEquals(savedNew.getCreatedAt(), savedNew.getUpdatedAt());
        assertTrue(savedNew.getIsSynced());
    }
}

