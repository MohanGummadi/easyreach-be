package com.easyreach.backend.service;

import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
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
import java.util.Collections;
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

    @Test
    void delete_marks_entity_deleted() {
        Company e = new Company();
        e.setUuid("c1");
        e.setChangeId(1L);
        when(repository.findByUuidAndDeletedIsFalse("c1")).thenReturn(java.util.Optional.of(e));

        service.delete("c1");

        assertTrue(e.isDeleted());
        assertNotNull(e.getDeletedAt());
        assertEquals(2L, e.getChangeId());
        verify(repository).save(e);
    }

    @Test
    void fetchChangesSince_includes_tombstones() {
        OffsetDateTime cursor = OffsetDateTime.now().minusDays(1);
        Company tombstone = new Company();
        tombstone.setUuid("c2");
        tombstone.setDeleted(true);
        tombstone.setDeletedAt(cursor.plusHours(1));

        when(repository.findByUuidAndUpdatedAtGreaterThanEqual(anyString(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(repository.findByUuidAndDeletedIsTrueAndDeletedAtGreaterThanEqual(anyString(), any(), any()))
                .thenReturn(List.of(tombstone));
        when(mapper.toDto(any())).thenReturn(new CompanyResponseDto());

        Map<String, Object> result = service.fetchChangesSince("c1", cursor, 10);
        assertEquals(List.of("c2"), result.get("tombstones"));
    }
}

