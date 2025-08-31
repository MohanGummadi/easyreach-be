package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;
import com.easyreach.backend.entity.ExpenseMaster;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class ExpenseMasterMapperImpl implements ExpenseMasterMapper {

    @Override
    public ExpenseMaster toEntity(ExpenseMasterRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        ExpenseMaster.ExpenseMasterBuilder expenseMaster = ExpenseMaster.builder();

        expenseMaster.id( dto.getId() );
        expenseMaster.expenseName( dto.getExpenseName() );
        expenseMaster.companyUuid( dto.getCompanyUuid() );
        expenseMaster.isSynced( dto.getIsSynced() );
        expenseMaster.createdBy( dto.getCreatedBy() );
        expenseMaster.createdAt( dto.getCreatedAt() );
        expenseMaster.updatedBy( dto.getUpdatedBy() );
        expenseMaster.updatedAt( dto.getUpdatedAt() );

        return expenseMaster.build();
    }

    @Override
    public ExpenseMasterResponseDto toDto(ExpenseMaster entity) {
        if ( entity == null ) {
            return null;
        }

        ExpenseMasterResponseDto expenseMasterResponseDto = new ExpenseMasterResponseDto();

        expenseMasterResponseDto.setId( entity.getId() );
        expenseMasterResponseDto.setExpenseName( entity.getExpenseName() );
        expenseMasterResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        expenseMasterResponseDto.setIsSynced( entity.getIsSynced() );
        expenseMasterResponseDto.setCreatedBy( entity.getCreatedBy() );
        expenseMasterResponseDto.setCreatedAt( entity.getCreatedAt() );
        expenseMasterResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        expenseMasterResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return expenseMasterResponseDto;
    }

    @Override
    public void update(ExpenseMaster entity, ExpenseMasterRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getExpenseName() != null ) {
            entity.setExpenseName( dto.getExpenseName() );
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
