package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.ExpenseMaster;
import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;

@Mapper(componentModel = "spring")
public interface ExpenseMasterMapper {
    ExpenseMaster toEntity(ExpenseMasterRequestDto dto);
    ExpenseMasterResponseDto toDto(ExpenseMaster entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget ExpenseMaster entity, ExpenseMasterRequestDto dto);
}
