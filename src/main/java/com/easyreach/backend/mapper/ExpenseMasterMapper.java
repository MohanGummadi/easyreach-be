package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.ExpenseMaster;
import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;

@Mapper(componentModel = "spring")
public interface ExpenseMasterMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    ExpenseMaster toEntity(ExpenseMasterRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    ExpenseMasterResponseDto toDto(ExpenseMaster entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget ExpenseMaster entity, ExpenseMasterRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(ExpenseMasterRequestDto dto, @MappingTarget ExpenseMaster entity);
}
