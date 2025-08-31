package com.easyreach.backend.entity;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyEntityTest {

    @Test
    void entityAnnotationAndBuilder() {
        assertThat(Company.class.isAnnotationPresent(Entity.class)).isTrue();
        Company company = Company.builder()
                .uuid("c1")
                .companyCode("code")
                .companyName("Name")
                .companyContactNo("123")
                .companyLocation("loc")
                .companyRegistrationDate(LocalDate.now())
                .ownerName("Owner")
                .ownerMobileNo("999")
                .ownerEmailAddress("owner@example.com")
                .ownerDob(LocalDate.now())
                .isActive(true)
                .isSynced(true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        assertThat(company.getCompanyName()).isEqualTo("Name");
    }
}
