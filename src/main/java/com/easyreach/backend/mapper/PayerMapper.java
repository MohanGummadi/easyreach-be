package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.Payer;
import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;

@Mapper(componentModel = "spring")
public interface PayerMapper {
    Payer toEntity(PayerRequestDto dto);
    PayerResponseDto toDto(Payer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Payer entity, PayerRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(PayerRequestDto dto, @MappingTarget Payer entity);
}
