package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.diesel_usage.DieselUsageRequestDto;
import com.easyreach.backend.dto.diesel_usage.DieselUsageResponseDto;
import com.easyreach.backend.entity.DieselUsage;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DieselUsageMapperTest {
    private final DieselUsageMapper mapper = Mappers.getMapper(DieselUsageMapper.class);

    @Test
    void toEntity_and_toDto() {
        OffsetDateTime now = OffsetDateTime.now();
        DieselUsageRequestDto req = new DieselUsageRequestDto("d1","v",now,new BigDecimal("5"),"c",true,"u",now,"u2",now);
        DieselUsage entity = mapper.toEntity(req);
        DieselUsageResponseDto dto = mapper.toDto(entity);
        assertThat(dto.getDieselUsageId()).isEqualTo("d1");
        assertThat(entity.getVehicleName()).isEqualTo("v");
    }

    @Test
    void update_ignoresNulls() {
        DieselUsage entity = new DieselUsage();
        entity.setVehicleName("v1");
        DieselUsageRequestDto req = new DieselUsageRequestDto();
        req.setVehicleName(null);
        req.setCompanyUuid("c2");
        mapper.update(entity, req);
        assertThat(entity.getVehicleName()).isEqualTo("v1");
        assertThat(entity.getCompanyUuid()).isEqualTo("c2");
    }
}

