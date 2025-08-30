package com.easyreach.companies;

import com.easyreach.backend.dto.CompanyDto;
import com.easyreach.backend.repositories.CompanyRepository;
import com.easyreach.backend.services.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CompanyServiceTest {

    @Autowired
    private CompanyService service;

    @Autowired
    private CompanyRepository repository;

    @Test
    void upsertCreatesAndUpdates() {
        CompanyDto dto = new CompanyDto();
        dto.setUuid("u1");
        dto.setCompanyId("c1");
        dto.setCompanyName("Comp1");
        dto.setCompanyContactNo("123");
        dto.setCompanyLocation("Loc");
        dto.setCompanyRegistrationDate(LocalDate.now());
        dto.setOwnerName("Owner");
        dto.setOwnerMobileNo("999");
        dto.setOwnerEmailAddress("owner@example.com");
        dto.setOwnerDOB(LocalDate.now());

        CompanyDto created = service.upsert(dto);
        assertThat(created.getCompanyName()).isEqualTo("Comp1");
        assertThat(repository.count()).isEqualTo(1);

        dto.setCompanyName("Comp2");
        CompanyDto updated = service.upsert(dto);
        assertThat(updated.getCompanyName()).isEqualTo("Comp2");
        assertThat(repository.count()).isEqualTo(1);
    }
}
