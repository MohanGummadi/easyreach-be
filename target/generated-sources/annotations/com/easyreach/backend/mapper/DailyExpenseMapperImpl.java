package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseResponseDto;
import com.easyreach.backend.entity.DailyExpense;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:19+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class DailyExpenseMapperImpl implements DailyExpenseMapper {

    @Override
    public DailyExpense toEntity(DailyExpenseRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        DailyExpense.DailyExpenseBuilder dailyExpense = DailyExpense.builder();

        dailyExpense.expenseId( dto.getExpenseId() );
        dailyExpense.expenseType( dto.getExpenseType() );
        dailyExpense.expenseAmount( dto.getExpenseAmount() );
        dailyExpense.expenseDate( dto.getExpenseDate() );
        dailyExpense.expenseNote( dto.getExpenseNote() );
        dailyExpense.isPaid( dto.getIsPaid() );
        dailyExpense.paidBy( dto.getPaidBy() );
        dailyExpense.paidTo( dto.getPaidTo() );
        dailyExpense.paidDate( dto.getPaidDate() );
        dailyExpense.companyUuid( dto.getCompanyUuid() );
        dailyExpense.userId( dto.getUserId() );
        dailyExpense.isSynced( dto.getIsSynced() );
        dailyExpense.createdBy( dto.getCreatedBy() );
        dailyExpense.createdAt( dto.getCreatedAt() );
        dailyExpense.updatedBy( dto.getUpdatedBy() );
        dailyExpense.updatedAt( dto.getUpdatedAt() );

        return dailyExpense.build();
    }

    @Override
    public DailyExpenseResponseDto toDto(DailyExpense entity) {
        if ( entity == null ) {
            return null;
        }

        DailyExpenseResponseDto dailyExpenseResponseDto = new DailyExpenseResponseDto();

        dailyExpenseResponseDto.setExpenseId( entity.getExpenseId() );
        dailyExpenseResponseDto.setExpenseType( entity.getExpenseType() );
        dailyExpenseResponseDto.setExpenseAmount( entity.getExpenseAmount() );
        dailyExpenseResponseDto.setExpenseDate( entity.getExpenseDate() );
        dailyExpenseResponseDto.setExpenseNote( entity.getExpenseNote() );
        dailyExpenseResponseDto.setIsPaid( entity.getIsPaid() );
        dailyExpenseResponseDto.setPaidBy( entity.getPaidBy() );
        dailyExpenseResponseDto.setPaidTo( entity.getPaidTo() );
        dailyExpenseResponseDto.setPaidDate( entity.getPaidDate() );
        dailyExpenseResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        dailyExpenseResponseDto.setUserId( entity.getUserId() );
        dailyExpenseResponseDto.setIsSynced( entity.getIsSynced() );
        dailyExpenseResponseDto.setCreatedBy( entity.getCreatedBy() );
        dailyExpenseResponseDto.setCreatedAt( entity.getCreatedAt() );
        dailyExpenseResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        dailyExpenseResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return dailyExpenseResponseDto;
    }

    @Override
    public void update(DailyExpense entity, DailyExpenseRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getExpenseId() != null ) {
            entity.setExpenseId( dto.getExpenseId() );
        }
        if ( dto.getExpenseType() != null ) {
            entity.setExpenseType( dto.getExpenseType() );
        }
        if ( dto.getExpenseAmount() != null ) {
            entity.setExpenseAmount( dto.getExpenseAmount() );
        }
        if ( dto.getExpenseDate() != null ) {
            entity.setExpenseDate( dto.getExpenseDate() );
        }
        if ( dto.getExpenseNote() != null ) {
            entity.setExpenseNote( dto.getExpenseNote() );
        }
        if ( dto.getIsPaid() != null ) {
            entity.setIsPaid( dto.getIsPaid() );
        }
        if ( dto.getPaidBy() != null ) {
            entity.setPaidBy( dto.getPaidBy() );
        }
        if ( dto.getPaidTo() != null ) {
            entity.setPaidTo( dto.getPaidTo() );
        }
        if ( dto.getPaidDate() != null ) {
            entity.setPaidDate( dto.getPaidDate() );
        }
        if ( dto.getCompanyUuid() != null ) {
            entity.setCompanyUuid( dto.getCompanyUuid() );
        }
        if ( dto.getUserId() != null ) {
            entity.setUserId( dto.getUserId() );
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
