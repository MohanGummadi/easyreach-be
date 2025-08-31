package com.easyreach.backend.repository;

import com.easyreach.backend.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository repository;

    @Test
    void saveAndFind() {
        Company company = Company.builder()
                .uuid("c1")
                .companyCode("code")
                .companyName("Name")
                .companyContactNo("123")
                .companyCoordinates("coords")
                .companyLocation("Loc")
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

        repository.save(company);
        assertThat(repository.findById("c1")).contains(company);
    }
}
