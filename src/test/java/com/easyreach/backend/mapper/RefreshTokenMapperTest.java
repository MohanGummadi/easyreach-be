package com.easyreach.backend.mapper;

import com.easyreach.backend.auth.entity.RefreshToken;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenMapperTest {
    private final RefreshTokenMapper mapper = Mappers.getMapper(RefreshTokenMapper.class);

    @Test
    void toEntity_and_toDto() {
        OffsetDateTime now = OffsetDateTime.now();
        RefreshTokenRequestDto req = new RefreshTokenRequestDto("j1","u1",now,now.plusDays(1),null,null,true,"c",now,"u",now);
        RefreshToken entity = mapper.toEntity(req);
        RefreshTokenResponseDto dto = mapper.toDto(entity);
        assertThat(dto.getJti()).isEqualTo("j1");
        assertThat(entity.getUserId()).isEqualTo("u1");
    }

    @Test
    void update_ignoresNulls() {
        RefreshToken entity = new RefreshToken();
        entity.setUserId("u1");
        RefreshTokenRequestDto req = new RefreshTokenRequestDto();
        req.setUserId(null);
        req.setRotatedFromJti("r1");
        mapper.update(entity, req);
        assertThat(entity.getUserId()).isEqualTo("u1");
        assertThat(entity.getRotatedFromJti()).isEqualTo("r1");
    }
}

