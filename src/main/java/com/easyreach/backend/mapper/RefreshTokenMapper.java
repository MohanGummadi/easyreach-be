package com.easyreach.backend.mapper;

import org.mapstruct.*;
import com.easyreach.backend.auth.entity.RefreshToken;
import com.easyreach.backend.dto.refresh_token.RefreshTokenRequestDto;
import com.easyreach.backend.dto.refresh_token.RefreshTokenResponseDto;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshToken toEntity(RefreshTokenRequestDto dto);
    RefreshTokenResponseDto toDto(RefreshToken entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget RefreshToken entity, RefreshTokenRequestDto dto);
}
