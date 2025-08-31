package com.easyreach.backend.mapper;

import com.easyreach.backend.auth.entity.RefreshToken;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class RefreshTokenMapperImpl implements RefreshTokenMapper {

    @Override
    public RefreshToken toEntity(RefreshTokenRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        RefreshToken.RefreshTokenBuilder refreshToken = RefreshToken.builder();

        refreshToken.jti( dto.getJti() );
        refreshToken.userId( dto.getUserId() );
        refreshToken.issuedAt( dto.getIssuedAt() );
        refreshToken.expiresAt( dto.getExpiresAt() );
        refreshToken.revokedAt( dto.getRevokedAt() );
        refreshToken.rotatedFromJti( dto.getRotatedFromJti() );
        refreshToken.isSynced( dto.getIsSynced() );
        refreshToken.createdBy( dto.getCreatedBy() );
        refreshToken.createdAt( dto.getCreatedAt() );
        refreshToken.updatedBy( dto.getUpdatedBy() );
        refreshToken.updatedAt( dto.getUpdatedAt() );

        return refreshToken.build();
    }

    @Override
    public RefreshTokenResponseDto toDto(RefreshToken entity) {
        if ( entity == null ) {
            return null;
        }

        RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();

        refreshTokenResponseDto.setJti( entity.getJti() );
        refreshTokenResponseDto.setUserId( entity.getUserId() );
        refreshTokenResponseDto.setIssuedAt( entity.getIssuedAt() );
        refreshTokenResponseDto.setExpiresAt( entity.getExpiresAt() );
        refreshTokenResponseDto.setRevokedAt( entity.getRevokedAt() );
        refreshTokenResponseDto.setRotatedFromJti( entity.getRotatedFromJti() );
        refreshTokenResponseDto.setIsSynced( entity.getIsSynced() );
        refreshTokenResponseDto.setCreatedBy( entity.getCreatedBy() );
        refreshTokenResponseDto.setCreatedAt( entity.getCreatedAt() );
        refreshTokenResponseDto.setUpdatedBy( entity.getUpdatedBy() );
        refreshTokenResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return refreshTokenResponseDto;
    }

    @Override
    public void update(RefreshToken entity, RefreshTokenRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getJti() != null ) {
            entity.setJti( dto.getJti() );
        }
        if ( dto.getUserId() != null ) {
            entity.setUserId( dto.getUserId() );
        }
        if ( dto.getIssuedAt() != null ) {
            entity.setIssuedAt( dto.getIssuedAt() );
        }
        if ( dto.getExpiresAt() != null ) {
            entity.setExpiresAt( dto.getExpiresAt() );
        }
        if ( dto.getRevokedAt() != null ) {
            entity.setRevokedAt( dto.getRevokedAt() );
        }
        if ( dto.getRotatedFromJti() != null ) {
            entity.setRotatedFromJti( dto.getRotatedFromJti() );
        }
        if ( dto.getIsSynced() != null ) {
            entity.setIsSynced( dto.getIsSynced() );
        }
        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedBy() != null ) {
            entity.setUpdatedBy( dto.getUpdatedBy() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
    }
}
