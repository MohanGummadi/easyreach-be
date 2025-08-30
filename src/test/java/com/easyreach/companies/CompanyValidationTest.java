package com.easyreach.companies;

import com.easyreach.backend.dto.CompanyDto;
import com.easyreach.backend.services.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CompanyValidationTest {

    @Autowired
    private CompanyService service;

    @Test
    void missingRequiredFields() {
        CompanyDto dto = new CompanyDto();
        assertThatThrownBy(() -> service.upsert(dto))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
