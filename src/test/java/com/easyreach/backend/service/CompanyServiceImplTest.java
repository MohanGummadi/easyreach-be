package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import com.easyreach.backend.entity.Company;
import com.easyreach.backend.mapper.CompanyMapper;
import com.easyreach.backend.repository.CompanyRepository;
import com.easyreach.backend.security.CompanyContext;
import com.easyreach.backend.service.impl.CompanyServiceImpl;
import com.easyreach.backend.support.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import jakarta.persistence.EntityNotFoundException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository repository;

    @Mock
    private CompanyMapper mapper;

    @InjectMocks
    private CompanyServiceImpl service;

    @BeforeEach
    void setUp() {
        CompanyContext.setCompanyId("cmp-1");
    }

    @AfterEach
    void tearDown() {
        CompanyContext.clear();
    }

    @Test
    void createSavesAndReturnsDto() {
        CompanyRequestDto request = TestData.companyRequest();
        Company entity = new Company();
        CompanyResponseDto responseDto = TestData.companyResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(responseDto);

        ApiResponse<CompanyResponseDto> response = service.create(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getData()).isEqualTo(responseDto);
        verify(repository).save(entity);
    }

    @Test
    void updateThrowsWhenNotFound() {
        when(repository.findByUuidAndDeletedIsFalse("missing")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update("missing", TestData.companyRequest()));
    }

    @Test
    void bulkSyncHandlesNullList() {
        int result = service.bulkSync(null);
        assertThat(result).isZero();
        verifyNoInteractions(repository, mapper);
    }

    @Test
    void fetchChangesSinceReturnsCursorAndHasMore() {
        OffsetDateTime cursor = OffsetDateTime.now().minusDays(1);
        Company updated = new Company();
        updated.setUpdatedAt(cursor.plusHours(1));
        when(repository.findByUuidAndUpdatedAtGreaterThanEqual(eq("cmp-1"), eq(cursor), any(PageRequest.class)))
                .thenReturn(List.of(updated, new Company()));

        Map<String, Object> result = service.fetchChangesSince("cmp-1", cursor, 1);

        assertThat(result.get("items")).asList().hasSize(1);
        assertThat(result.get("hasMore")).isEqualTo(true);
        assertThat(result.get("cursorEnd")).isEqualTo(updated.getUpdatedAt());
        verify(repository).findByUuidAndUpdatedAtGreaterThanEqual(eq("cmp-1"), eq(cursor), any(PageRequest.class));
    }
}
