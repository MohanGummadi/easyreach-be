package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;
import com.easyreach.backend.entity.Company;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyMapperTest {

    private final CompanyMapper mapper = Mappers.getMapper(CompanyMapper.class);

    @Test
    void toEntity_and_toDto() {
        String uuid = "uuid-1";
        String companyCode = "C001";
        String companyName = "TestCo";
        String companyContactNo = "1234567890";
        String companyCoordinates = "12.34,56.78";
        String companyLocation = "City";
        LocalDate companyRegistrationDate = LocalDate.of(2023,1,1);
        String ownerName = "Owner";
        String ownerMobileNo = "0987654321";
        String ownerEmailAddress = "owner@test.com";
        LocalDate ownerDob = LocalDate.of(1990,2,3);
        Boolean isActive = true;
        Boolean isSynced = false;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-01-05T10:15:30Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-01-06T10:15:30Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-01-07T10:15:30Z");
        Long changeId = 5L;

        CompanyRequestDto dto = new CompanyRequestDto();
        dto.setUuid(uuid);
        dto.setCompanyCode(companyCode);
        dto.setCompanyName(companyName);
        dto.setCompanyContactNo(companyContactNo);
        dto.setCompanyCoordinates(companyCoordinates);
        dto.setCompanyLocation(companyLocation);
        dto.setCompanyRegistrationDate(companyRegistrationDate);
        dto.setOwnerName(ownerName);
        dto.setOwnerMobileNo(ownerMobileNo);
        dto.setOwnerEmailAddress(ownerEmailAddress);
        dto.setOwnerDob(ownerDob);
        dto.setIsActive(isActive);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);
        dto.setDeleted(deleted);
        dto.setDeletedAt(deletedAt);
        dto.setChangeId(changeId);

        Company expectedEntity = Company.builder()
                .uuid(uuid)
                .companyCode(companyCode)
                .companyName(companyName)
                .companyContactNo(companyContactNo)
                .companyCoordinates(companyCoordinates)
                .companyLocation(companyLocation)
                .companyRegistrationDate(companyRegistrationDate)
                .ownerName(ownerName)
                .ownerMobileNo(ownerMobileNo)
                .ownerEmailAddress(ownerEmailAddress)
                .ownerDob(ownerDob)
                .isActive(isActive)
                .isSynced(isSynced)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .deleted(deleted)
                .deletedAt(deletedAt)
                .changeId(changeId)
                .build();

        Company entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        CompanyResponseDto expectedDto = new CompanyResponseDto();
        expectedDto.setUuid(uuid);
        expectedDto.setCompanyCode(companyCode);
        expectedDto.setCompanyName(companyName);
        expectedDto.setCompanyContactNo(companyContactNo);
        expectedDto.setCompanyCoordinates(companyCoordinates);
        expectedDto.setCompanyLocation(companyLocation);
        expectedDto.setCompanyRegistrationDate(companyRegistrationDate);
        expectedDto.setOwnerName(ownerName);
        expectedDto.setOwnerMobileNo(ownerMobileNo);
        expectedDto.setOwnerEmailAddress(ownerEmailAddress);
        expectedDto.setOwnerDob(ownerDob);
        expectedDto.setIsActive(isActive);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        CompanyResponseDto actualDto = mapper.toDto(expectedEntity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

