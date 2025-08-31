package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;
import com.easyreach.backend.entity.PayerSettlement;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:19+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class PayerSettlementMapperImpl implements PayerSettlementMapper {

    @Override
    public PayerSettlement toEntity(PayerSettlementRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        PayerSettlement.PayerSettlementBuilder payerSettlement = PayerSettlement.builder();

        payerSettlement.settlementId( dto.getSettlementId() );
        payerSettlement.payerId( dto.getPayerId() );
        payerSettlement.amount( dto.getAmount() );
        payerSettlement.date( dto.getDate() );
        payerSettlement.companyUuid( dto.getCompanyUuid() );
        payerSettlement.isSynced( dto.getIsSynced() );
        payerSettlement.createdBy( dto.getCreatedBy() );
        payerSettlement.createdAt( dto.getCreatedAt() );
        payerSettlement.updatedBy( dto.getUpdatedBy() );
        payerSettlement.updatedAt( dto.getUpdatedAt() );

        return payerSettlement.build();
    }

    @Override
    public PayerSettlementResponseDto toDto(PayerSettlement entity) {
        if ( entity == null ) {
            return null;
        }

        PayerSettlementResponseDto payerSettlementResponseDto = new PayerSettlementResponseDto();

        payerSettlementResponseDto.setSettlementId( entity.getSettlementId() );
        payerSettlementResponseDto.setPayerId( entity.getPayerId() );
        payerSettlementResponseDto.setAmount( entity.getAmount() );
        payerSettlementResponseDto.setDate( entity.getDate() );
        payerSettlementResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        payerSettlementResponseDto.setIsSynced( entity.getIsSynced() );
        payerSettlementResponseDto.setCreatedBy( entity.getCreatedBy() );
        payerSettlementResponseDto.setCreatedAt( entity.getCreatedAt() );
        payerSettlementResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        payerSettlementResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return payerSettlementResponseDto;
    }

    @Override
    public void update(PayerSettlement entity, PayerSettlementRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getSettlementId() != null ) {
            entity.setSettlementId( dto.getSettlementId() );
        }
        if ( dto.getPayerId() != null ) {
            entity.setPayerId( dto.getPayerId() );
        }
        if ( dto.getAmount() != null ) {
            entity.setAmount( dto.getAmount() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        if ( dto.getCompanyUuid() != null ) {
            entity.setCompanyUuid( dto.getCompanyUuid() );
        }
        if ( dto.getIsSynced() != null ) {
            entity.setIsSynced( dto.getIsSynced() );
        }
        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedBy() != null ) {
            entity.setUpdatedBy( dto.getUpdatedBy() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
    }
}
