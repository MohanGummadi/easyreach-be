package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.Company;
import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    Company toEntity(CompanyRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    CompanyResponseDto toDto(Company entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Company entity, CompanyRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(CompanyRequestDto dto, @MappingTarget Company entity);
}
