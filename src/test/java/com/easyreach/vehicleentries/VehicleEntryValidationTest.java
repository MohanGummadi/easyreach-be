package com.easyreach.vehicleentries;

import com.easyreach.backend.dto.VehicleEntryDto;
import com.easyreach.backend.services.VehicleEntryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class VehicleEntryValidationTest {

    @Autowired
    private VehicleEntryService service;

    @Test
    void missingRequiredFields() {
        VehicleEntryDto dto = new VehicleEntryDto();
        assertThatThrownBy(() -> service.upsert(dto))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
