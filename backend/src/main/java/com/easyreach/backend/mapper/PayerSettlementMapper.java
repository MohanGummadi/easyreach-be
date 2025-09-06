package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.entity.PayerSettlement;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;

@Mapper(componentModel = "spring")
public interface PayerSettlementMapper {
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    PayerSettlement toEntity(PayerSettlementRequestDto dto);

    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "deletedAt", source = "deletedAt")
    @Mapping(target = "changeId", source = "changeId")
    PayerSettlementResponseDto toDto(PayerSettlement entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget PayerSettlement entity, PayerSettlementRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(PayerSettlementRequestDto dto, @MappingTarget PayerSettlement entity);
}
