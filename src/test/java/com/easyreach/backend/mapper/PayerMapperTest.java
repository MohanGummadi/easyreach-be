package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.entity.Payer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PayerMapperTest {

    private final PayerMapper mapper = Mappers.getMapper(PayerMapper.class);

    @Test
    void toEntity_and_toDto() {
        String payerId = "p1";
        String payerName = "John Doe";
        String mobileNo = "1234567890";
        String payerAddress = "Addr";
        LocalDate registrationDate = LocalDate.of(2023,3,1);
        Integer creditLimit = 5000;
        String companyUuid = "comp1";
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-05-01T00:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-05-02T00:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-05-03T00:00:00Z");
        Long changeId = 20L;

        PayerRequestDto dto = new PayerRequestDto();
        dto.setPayerId(payerId);
        dto.setPayerName(payerName);
        dto.setMobileNo(mobileNo);
        dto.setPayerAddress(payerAddress);
        dto.setRegistrationDate(registrationDate);
        dto.setCreditLimit(creditLimit);
        dto.setCompanyUuid(companyUuid);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        Payer expectedEntity = Payer.builder()
                .payerId(payerId)
                .payerName(payerName)
                .mobileNo(mobileNo)
                .payerAddress(payerAddress)
                .registrationDate(registrationDate)
                .creditLimit(creditLimit)
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

        Payer entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        PayerResponseDto expectedDto = new PayerResponseDto();
        expectedDto.setPayerId(payerId);
        expectedDto.setPayerName(payerName);
        expectedDto.setMobileNo(mobileNo);
        expectedDto.setPayerAddress(payerAddress);
        expectedDto.setRegistrationDate(registrationDate);
        expectedDto.setCreditLimit(creditLimit);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        PayerResponseDto actualDto = mapper.toDto(expectedEntity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

