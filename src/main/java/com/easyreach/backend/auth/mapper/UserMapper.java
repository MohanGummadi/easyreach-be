package com.easyreach.backend.auth.mapper;

import com.easyreach.backend.auth.dto.UserDto;
import com.easyreach.backend.auth.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);
    UserDto toDto(User entity);
}
