package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.daily_expenses.DailyExpenseRequestDto;
import com.easyreach.backend.dto.daily_expenses.DailyExpenseResponseDto;
import com.easyreach.backend.entity.DailyExpense;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DailyExpenseMapperTest {

    private final DailyExpenseMapper mapper = Mappers.getMapper(DailyExpenseMapper.class);

    @Test
    void toEntity_and_toDto() {
        String expenseId = "e1";
        String expenseType = "Fuel";
        BigDecimal expenseAmount = new BigDecimal("100.50");
        OffsetDateTime expenseDate = OffsetDateTime.parse("2023-04-01T10:00:00Z");
        String expenseNote = "note";
        Boolean isPaid = false;
        String paidBy = "John";
        String paidTo = "Doe";
        OffsetDateTime paidDate = OffsetDateTime.parse("2023-04-02T10:00:00Z");
        String companyUuid = "comp1";
        String userId = "u1";
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-04-03T10:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-04-04T10:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-04-05T10:00:00Z");
        Long changeId = 5L;

        DailyExpenseRequestDto dto = new DailyExpenseRequestDto();
        dto.setExpenseId(expenseId);
        dto.setExpenseType(expenseType);
        dto.setExpenseAmount(expenseAmount);
        dto.setExpenseDate(expenseDate);
        dto.setExpenseNote(expenseNote);
        dto.setIsPaid(isPaid);
        dto.setPaidBy(paidBy);
        dto.setPaidTo(paidTo);
        dto.setPaidDate(paidDate);
        dto.setCompanyUuid(companyUuid);
        dto.setUserId(userId);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        DailyExpense expectedEntity = DailyExpense.builder()
                .expenseId(expenseId)
                .expenseType(expenseType)
                .expenseAmount(expenseAmount)
                .expenseDate(expenseDate)
                .expenseNote(expenseNote)
                .isPaid(isPaid)
                .paidBy(paidBy)
                .paidTo(paidTo)
                .paidDate(paidDate)
                .companyUuid(companyUuid)
                .userId(userId)
                .isSynced(isSynced)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .deleted(deleted)
                .deletedAt(deletedAt)
                .changeId(changeId)
                .build();

        DailyExpense entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        DailyExpenseResponseDto expectedDto = new DailyExpenseResponseDto();
        expectedDto.setExpenseId(expenseId);
        expectedDto.setExpenseType(expenseType);
        expectedDto.setExpenseAmount(expenseAmount);
        expectedDto.setExpenseDate(expenseDate);
        expectedDto.setExpenseNote(expenseNote);
        expectedDto.setIsPaid(isPaid);
        expectedDto.setPaidBy(paidBy);
        expectedDto.setPaidTo(paidTo);
        expectedDto.setPaidDate(paidDate);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setUserId(userId);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        DailyExpenseResponseDto actualDto = mapper.toDto(entity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}
