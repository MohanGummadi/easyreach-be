package com.easyreach.backend.repository;

import com.easyreach.backend.entity.VehicleEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
class VehicleEntryRepositoryTest {

    @Autowired
    private VehicleEntryRepository repository;

    @Test
    void saveAndFind() {
        OffsetDateTime now = OffsetDateTime.now();
        VehicleEntry entry = VehicleEntry.builder()
                .entryId("e1")
                .companyUuid("c1")
                .payerId("p1")
                .vehicleNumber("ABC123")
                .vehicleType("Truck")
                .fromAddress("from")
                .toAddress("to")
                .driverName("Driver")
                .driverContactNo("999")
                .commission(BigDecimal.ONE)
                .beta(BigDecimal.ONE)
                .amount(BigDecimal.TEN)
                .paytype("CASH")
                .entryDate(LocalDate.now())
                .entryTime(now)
                .paidAmount(BigDecimal.ONE)
                .pendingAmt(BigDecimal.ZERO)
                .isSettled(false)
                .isSynced(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        repository.save(entry);
        assertThat(repository.findById("e1")).contains(entry);
    }
}
