package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.vehicle_entries.VehicleEntryRequestDto;
import com.easyreach.backend.dto.vehicle_entries.VehicleEntryResponseDto;
import com.easyreach.backend.entity.VehicleEntry;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleEntryMapperTest {
    private final VehicleEntryMapper mapper = Mappers.getMapper(VehicleEntryMapper.class);

    @Test
    void toEntityAndToDto() {
        OffsetDateTime now = OffsetDateTime.now();
        VehicleEntryRequestDto req = new VehicleEntryRequestDto(
                "e1","c1","p1","ABC123","Truck","from","to","driver","999",
                BigDecimal.ONE, BigDecimal.ONE, "ref", BigDecimal.TEN, "CASH", LocalDate.now(), now,
                null, null, null, BigDecimal.ONE, BigDecimal.ZERO, false, null, null, true,
                "creator", now, "upd", now);

        VehicleEntry entity = mapper.toEntity(req);
        VehicleEntryResponseDto dto = mapper.toDto(entity);

        assertThat(dto.getVehicleNumber()).isEqualTo(req.getVehicleNumber());
    }
}
