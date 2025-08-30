package com.easyreach.backend.auth.mapper;

import com.easyreach.backend.auth.dto.UserDto;
import com.easyreach.backend.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "isActive", expression = "java(dto.getIsActive() == null ? null : dto.getIsActive() == 1)")
    User toEntity(UserDto dto);

    @Mapping(target = "isActive", expression = "java(entity.getIsActive() == null ? null : (entity.getIsActive() ? 1 : 0))")
    UserDto toDto(User entity);
}
