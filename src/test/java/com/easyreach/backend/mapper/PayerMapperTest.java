package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.payers.PayerRequestDto;
import com.easyreach.backend.dto.payers.PayerResponseDto;
import com.easyreach.backend.entity.Payer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PayerMapperTest {
    private final PayerMapper mapper = Mappers.getMapper(PayerMapper.class);

    @Test
    void toEntityAndToDto() {
        OffsetDateTime now = OffsetDateTime.now();
        PayerRequestDto req = new PayerRequestDto(
                "p1","name","123","addr", LocalDate.now(),100,"c1",true,
                "creator", now, "upd", now, null);

        Payer entity = mapper.toEntity(req);
        PayerResponseDto dto = mapper.toDto(entity);

        assertThat(dto.getPayerName()).isEqualTo(req.getPayerName());
    }
}
