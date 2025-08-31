package com.easyreach.backend.repository;

import com.easyreach.backend.entity.Company;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.flyway.locations=classpath:db/migration"
})
class CompanyRepositoryTest {
    @Autowired
    private CompanyRepository repository;
    @Autowired
    private Flyway flyway;

    @BeforeEach
    void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void saveAndFind() {
        Company c = Company.builder()
                .uuid("u1")
                .companyCode("c1")
                .companyName("name")
                .companyContactNo("123")
                .companyLocation("loc")
                .companyRegistrationDate(LocalDate.now())
                .ownerName("owner")
                .ownerMobileNo("999")
                .ownerEmailAddress("owner@ex.com")
                .ownerDob(LocalDate.now())
                .isActive(true)
                .isSynced(true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        repository.save(c);
        Optional<Company> found = repository.findById("u1");
        assertThat(found).isPresent();
        assertThat(found.get().getCompanyName()).isEqualTo("name");
    }
}
