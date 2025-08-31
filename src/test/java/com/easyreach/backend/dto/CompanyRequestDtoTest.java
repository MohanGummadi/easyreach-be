package com.easyreach.backend.dto;

import com.easyreach.backend.dto.companies.CompanyRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyRequestDtoTest {

    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();

    @Test
    void serializeAndDeserialize() throws Exception {
        CompanyRequestDto dto = new CompanyRequestDto(
                "u1","c1","name","123","coord","loc", LocalDate.now(),"owner","999","owner@ex.com",
                LocalDate.now(), true, true, "creator", OffsetDateTime.now(), "upd", OffsetDateTime.now());

        String json = mapper.writeValueAsString(dto);
        CompanyRequestDto read = mapper.readValue(json, CompanyRequestDto.class);
        assertThat(read.getCompanyName()).isEqualTo(dto.getCompanyName());
    }
}
