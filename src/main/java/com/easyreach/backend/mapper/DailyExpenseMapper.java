package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.DailyExpense;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseResponseDto;

@Mapper(componentModel = "spring")
public interface DailyExpenseMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    DailyExpense toEntity(DailyExpenseRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    DailyExpenseResponseDto toDto(DailyExpense entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget DailyExpense entity, DailyExpenseRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(DailyExpenseRequestDto dto, @MappingTarget DailyExpense entity);
}
