package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.Company;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toEntity(CompanyRequestDto dto);
    CompanyResponseDto toDto(Company entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Company entity, CompanyRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(CompanyRequestDto dto, @MappingTarget Company entity);
}
