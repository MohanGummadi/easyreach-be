package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;
import com.easyreach.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toEntity_and_toDto() {
        UserRequestDto req = new UserRequestDto();
        req.setId("u1");
        req.setEmployeeId("e1");
        req.setIsActive(true);
        req.setCreatedAt(OffsetDateTime.now());
        req.setUpdatedAt(OffsetDateTime.now());

        User entity = mapper.toEntity(req);
        UserResponseDto dto = mapper.toDto(entity);

        assertThat(dto.getId()).isEqualTo("u1");
        assertThat(entity.getId()).isEqualTo("u1");
    }
}
