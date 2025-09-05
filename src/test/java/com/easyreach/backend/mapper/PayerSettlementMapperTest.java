package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.payer_settlements.PayerSettlementRequestDto;
import com.easyreach.backend.dto.payer_settlements.PayerSettlementResponseDto;
import com.easyreach.backend.entity.PayerSettlement;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PayerSettlementMapperTest {

    private final PayerSettlementMapper mapper = Mappers.getMapper(PayerSettlementMapper.class);

    @Test
    void toEntity_and_toDto() {
        String settlementId = "st1";
        String payerId = "p1";
        BigDecimal amount = new BigDecimal("100.50");
        OffsetDateTime date = OffsetDateTime.parse("2023-03-01T12:00:00Z");
        String companyUuid = "comp1";
        Boolean isSynced = false;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-03-02T12:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-03-03T12:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-03-04T12:00:00Z");
        Long changeId = 15L;

        PayerSettlementRequestDto dto = new PayerSettlementRequestDto();
        dto.setSettlementId(settlementId);
        dto.setPayerId(payerId);
        dto.setAmount(amount);
        dto.setDate(date);
        dto.setCompanyUuid(companyUuid);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        PayerSettlement expectedEntity = PayerSettlement.builder()
                .settlementId(settlementId)
                .payerId(payerId)
                .amount(amount)
                .date(date)
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

        PayerSettlement entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        PayerSettlementResponseDto expectedDto = new PayerSettlementResponseDto();
        expectedDto.setSettlementId(settlementId);
        expectedDto.setPayerId(payerId);
        expectedDto.setAmount(amount);
        expectedDto.setDate(date);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        PayerSettlementResponseDto actualDto = mapper.toDto(expectedEntity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

