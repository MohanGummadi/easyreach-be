package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.PayerSettlement;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;

@Mapper(componentModel = "spring")
public interface PayerSettlementMapper {
    PayerSettlement toEntity(PayerSettlementRequestDto dto);
    PayerSettlementResponseDto toDto(PayerSettlement entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget PayerSettlement entity, PayerSettlementRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(PayerSettlementRequestDto dto, @MappingTarget PayerSettlement entity);
}
