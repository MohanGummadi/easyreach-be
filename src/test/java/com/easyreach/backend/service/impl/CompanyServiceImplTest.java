package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import com.easyreach.backend.entity.Company;
import com.easyreach.backend.mapper.CompanyMapper;
import com.easyreach.backend.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {
    @Mock
    private CompanyRepository repository;
    @Mock
    private CompanyMapper mapper;
    @InjectMocks
    private CompanyServiceImpl service;

    @Test
    void create_returnsDto() {
        CompanyRequestDto req = new CompanyRequestDto();
        Company entity = new Company();
        CompanyResponseDto resp = new CompanyResponseDto();
        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(resp);

        ApiResponse<CompanyResponseDto> result = service.create(req);

        assertThat(result.getData()).isEqualTo(resp);
        verify(repository).save(entity);
    }

    @Test
    void get_notFound_throws() {
        when(repository.findById("id")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get("id"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void list_returnsPage() {
        Company entity = new Company();
        CompanyResponseDto dto = new CompanyResponseDto();
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDto(entity)).thenReturn(dto);

        ApiResponse<Page<CompanyResponseDto>> res = service.list(Pageable.unpaged());

        assertThat(res.getData().getContent()).containsExactly(dto);
    }

    @Test
    void bulkSync_savesEntities() {
        CompanyRequestDto dto = new CompanyRequestDto();
        Company entity = new Company();
        when(mapper.toEntity(dto)).thenReturn(entity);

        int count = service.bulkSync(List.of(dto));

        assertThat(count).isEqualTo(1);
        verify(repository).saveAll(any());
    }
}
