package com.easyreach.vehicleentries;

import com.easyreach.vehicleentries.dto.VehicleEntryDto;
import com.easyreach.vehicleentries.repository.VehicleEntryRepository;
import com.easyreach.vehicleentries.service.VehicleEntryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class VehicleEntryServiceTest {

    @Autowired
    private VehicleEntryService service;

    @Autowired
    private VehicleEntryRepository repository;

    @Test
    void upsertCreatesAndUpdates() {
        VehicleEntryDto dto = new VehicleEntryDto();
        dto.setEntryId("1");
        dto.setCompanyId("c1");
        dto.setPaytype("cash");
        VehicleEntryDto created = service.upsert(dto);
        assertThat(created.getPaytype()).isEqualTo("cash");
        assertThat(repository.count()).isEqualTo(1);

        dto.setPaytype("card");
        VehicleEntryDto updated = service.upsert(dto);
        assertThat(updated.getPaytype()).isEqualTo("card");
        assertThat(repository.count()).isEqualTo(1);
    }
}
