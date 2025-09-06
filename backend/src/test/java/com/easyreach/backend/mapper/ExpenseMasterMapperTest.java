package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.expense_master.ExpenseMasterRequestDto;
import com.easyreach.backend.dto.expense_master.ExpenseMasterResponseDto;
import com.easyreach.backend.entity.ExpenseMaster;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ExpenseMasterMapperTest {

    private final ExpenseMasterMapper mapper = Mappers.getMapper(ExpenseMasterMapper.class);

    @Test
    void toEntity_and_toDto() {
        String id = "em1";
        String expenseName = "Misc";
        String companyUuid = "comp1";
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-06-01T00:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-06-02T00:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-06-03T00:00:00Z");
        Long changeId = 7L;

        ExpenseMasterRequestDto dto = new ExpenseMasterRequestDto();
        dto.setId(id);
        dto.setExpenseName(expenseName);
        dto.setCompanyUuid(companyUuid);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        ExpenseMaster expectedEntity = ExpenseMaster.builder()
                .id(id)
                .expenseName(expenseName)
                .companyUuid(companyUuid)
                .isSynced(isSynced)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .deleted(deleted)
                .deletedAt(deletedAt)
                .changeId(changeId)
                .build();

        ExpenseMaster entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        ExpenseMasterResponseDto expectedDto = new ExpenseMasterResponseDto();
        expectedDto.setId(id);
        expectedDto.setExpenseName(expenseName);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        ExpenseMasterResponseDto actualDto = mapper.toDto(entity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}
