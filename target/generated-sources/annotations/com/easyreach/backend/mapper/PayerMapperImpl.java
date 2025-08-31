package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.entity.Payer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class PayerMapperImpl implements PayerMapper {

    @Override
    public Payer toEntity(PayerRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Payer.PayerBuilder payer = Payer.builder();

        payer.payerId( dto.getPayerId() );
        payer.payerName( dto.getPayerName() );
        payer.mobileNo( dto.getMobileNo() );
        payer.payerAddress( dto.getPayerAddress() );
        payer.registrationDate( dto.getRegistrationDate() );
        payer.creditLimit( dto.getCreditLimit() );
        payer.companyUuid( dto.getCompanyUuid() );
        payer.isSynced( dto.getIsSynced() );
        payer.createdBy( dto.getCreatedBy() );
        payer.createdAt( dto.getCreatedAt() );
        payer.updatedBy( dto.getUpdatedBy() );
        payer.updatedAt( dto.getUpdatedAt() );
        payer.deletedAt( dto.getDeletedAt() );

        return payer.build();
    }

    @Override
    public PayerResponseDto toDto(Payer entity) {
        if ( entity == null ) {
            return null;
        }

        PayerResponseDto payerResponseDto = new PayerResponseDto();

        payerResponseDto.setPayerId( entity.getPayerId() );
        payerResponseDto.setPayerName( entity.getPayerName() );
        payerResponseDto.setMobileNo( entity.getMobileNo() );
        payerResponseDto.setPayerAddress( entity.getPayerAddress() );
        payerResponseDto.setRegistrationDate( entity.getRegistrationDate() );
        payerResponseDto.setCreditLimit( entity.getCreditLimit() );
        payerResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        payerResponseDto.setIsSynced( entity.getIsSynced() );
        payerResponseDto.setCreatedBy( entity.getCreatedBy() );
        payerResponseDto.setCreatedAt( entity.getCreatedAt() );
        payerResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        payerResponseDto.setUpdatedAt( entity.getUpdatedAt() );
        payerResponseDto.setDeletedAt( entity.getDeletedAt() );

        return payerResponseDto;
    }

    @Override
    public void update(Payer entity, PayerRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPayerId() != null ) {
            entity.setPayerId( dto.getPayerId() );
        }
        if ( dto.getPayerName() != null ) {
            entity.setPayerName( dto.getPayerName() );
        }
        if ( dto.getMobileNo() != null ) {
            entity.setMobileNo( dto.getMobileNo() );
        }
        if ( dto.getPayerAddress() != null ) {
            entity.setPayerAddress( dto.getPayerAddress() );
        }
        if ( dto.getRegistrationDate() != null ) {
            entity.setRegistrationDate( dto.getRegistrationDate() );
        }
        if ( dto.getCreditLimit() != null ) {
            entity.setCreditLimit( dto.getCreditLimit() );
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
        if ( dto.getDeletedAt() != null ) {
            entity.setDeletedAt( dto.getDeletedAt() );
        }
    }
}
