package com.easyreach.backend.support;

import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.easyreach.backend.dto.companies.CompanyResponseDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Utility builders for frequently used domain objects in tests.
 */
public final class TestData {

    private TestData() {
    }

    public static CompanyRequestDto companyRequest() {
        return new CompanyRequestDto(
                "uuid-1",
                "CODE",
                "Company",
                "1234567890",
                "12.34,56.78",
                "Location",
                LocalDate.of(2020, 1, 1),
                "Owner",
                "9876543210",
                "owner@example.com",
                LocalDate.of(1990, 1, 1),
                true,
                true,
                "creator",
                OffsetDateTime.now().minusDays(1),
                "updater",
                OffsetDateTime.now(),
                false,
                null,
                1L
        );
    }

    public static CompanyResponseDto companyResponse() {
        CompanyRequestDto req = companyRequest();
        return new CompanyResponseDto(
                req.getUuid(),
                req.getCompanyCode(),
                req.getCompanyName(),
                req.getCompanyContactNo(),
                req.getCompanyCoordinates(),
                req.getCompanyLocation(),
                req.getCompanyRegistrationDate(),
                req.getOwnerName(),
                req.getOwnerMobileNo(),
                req.getOwnerEmailAddress(),
                req.getOwnerDob(),
                req.getIsActive(),
                req.getIsSynced(),
                req.getCreatedBy(),
                req.getCreatedAt(),
                req.getUpdatedBy(),
                req.getUpdatedAt(),
                req.getDeleted(),
                req.getDeletedAt(),
                req.getChangeId()
        );
    }
}
