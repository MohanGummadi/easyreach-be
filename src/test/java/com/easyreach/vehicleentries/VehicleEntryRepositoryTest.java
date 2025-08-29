package com.easyreach.vehicleentries;

import com.easyreach.backend.entities.VehicleEntry;
import com.easyreach.backend.repositories.VehicleEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VehicleEntryRepositoryTest {

    @Autowired
    private VehicleEntryRepository repository;

    @Test
    void findByCompanyIdAndCreatedAtBetween() {
        VehicleEntry e1 = new VehicleEntry();
        e1.setEntryId("1");
        e1.setCompanyId("c1");
        e1.setCreatedAt(LocalDateTime.now().minusDays(2));
        repository.save(e1);

        VehicleEntry e2 = new VehicleEntry();
        e2.setEntryId("2");
        e2.setCompanyId("c1");
        e2.setCreatedAt(LocalDateTime.now().minusDays(1));
        repository.save(e2);

        LocalDateTime start = LocalDateTime.now().minusDays(1).minusHours(1);
        LocalDateTime end = LocalDateTime.now();

        Page<VehicleEntry> page = repository.findByCompanyIdAndCreatedAtBetween("c1", start, end, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getEntryId()).isEqualTo("2");
    }
}
