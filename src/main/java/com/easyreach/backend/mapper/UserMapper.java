package com.easyreach.backend.mapper;

import com.easyreach.backend.entity.User;
import org.mapstruct.*;
import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto dto);
    UserResponseDto toDto(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget User entity, UserRequestDto dto);
}
