package com.easyreach.backend.mappers;

import com.easyreach.backend.dto.CompanyDto;
import com.easyreach.backend.entities.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toEntity(CompanyDto dto);
    CompanyDto toDto(Company entity);
}
