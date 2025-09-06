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
        String jti = "jti1";
        String userId = "user1";
        OffsetDateTime issuedAt = OffsetDateTime.parse("2023-04-01T00:00:00Z");
        OffsetDateTime expiresAt = OffsetDateTime.parse("2023-04-02T00:00:00Z");
        OffsetDateTime revokedAt = OffsetDateTime.parse("2023-04-03T00:00:00Z");
        String rotatedFromJti = "prev";
        Boolean isSynced = true;
        String createdBy = "creator";
        OffsetDateTime createdAt = OffsetDateTime.parse("2023-04-04T00:00:00Z");
        String updatedBy = "upd";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-04-05T00:00:00Z");

        RefreshTokenRequestDto dto = new RefreshTokenRequestDto();
        dto.setJti(jti);
        dto.setUserId(userId);
        dto.setIssuedAt(issuedAt);
        dto.setExpiresAt(expiresAt);
        dto.setRevokedAt(revokedAt);
        dto.setRotatedFromJti(rotatedFromJti);
        dto.setIsSynced(isSynced);
        dto.setCreatedBy(createdBy);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedBy(updatedBy);
        dto.setUpdatedAt(updatedAt);

        RefreshToken expectedEntity = RefreshToken.builder()
                .jti(jti)
                .userId(userId)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .revokedAt(revokedAt)
                .rotatedFromJti(rotatedFromJti)
                .isSynced(isSynced)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();

        RefreshToken entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        RefreshTokenResponseDto expectedDto = new RefreshTokenResponseDto();
        expectedDto.setJti(jti);
        expectedDto.setUserId(userId);
        expectedDto.setIssuedAt(issuedAt);
        expectedDto.setExpiresAt(expiresAt);
        expectedDto.setRevokedAt(revokedAt);
        expectedDto.setRotatedFromJti(rotatedFromJti);
        expectedDto.setIsSynced(isSynced);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedBy(updatedBy);
        expectedDto.setUpdatedAt(updatedAt);

        RefreshTokenResponseDto actualDto = mapper.toDto(expectedEntity);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

