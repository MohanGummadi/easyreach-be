package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import com.easyreach.backend.entity.Company;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyMapperTest {
    private final CompanyMapper mapper = Mappers.getMapper(CompanyMapper.class);

    @Test
    void toEntity_and_toDto() {
        CompanyRequestDto req = new CompanyRequestDto(
                "u1","c1","name","123","coord","loc", LocalDate.now(),"owner","999","owner@ex.com",
                LocalDate.now(), true, true, "creator", OffsetDateTime.now(), "upd", OffsetDateTime.now());

        Company entity = mapper.toEntity(req);
        CompanyResponseDto dto = mapper.toDto(entity);

        assertThat(dto.getCompanyName()).isEqualTo(req.getCompanyName());
    }
}
