package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.DailyExpense;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseResponseDto;

@Mapper(componentModel = "spring")
public interface DailyExpenseMapper {
    DailyExpense toEntity(DailyExpenseRequestDto dto);
    DailyExpenseResponseDto toDto(DailyExpense entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget DailyExpense entity, DailyExpenseRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(DailyExpenseRequestDto dto, @MappingTarget DailyExpense entity);
}
