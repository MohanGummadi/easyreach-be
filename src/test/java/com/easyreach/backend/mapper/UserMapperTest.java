package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;
import com.easyreach.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toEntity_and_toDto() {
        String id = "u1";
        String employeeId = "emp1";
        String email = "user@test.com";
        String mobileNo = "1234567890";
        String password = "pass";
        String role = "ADMIN";
        String name = "User";
        String companyUuid = "comp1";
        String companyCode = "code1";
        String createdBy = "creator";
        String location = "City";
        LocalDate dateOfBirth = LocalDate.of(1990,1,1);
        LocalDate joiningDate = LocalDate.of(2020,1,1);
        Boolean isActive = true;

        UserRequestDto dto = new UserRequestDto();
        dto.setId(id);
        dto.setEmployeeId(employeeId);
        dto.setEmail(email);
        dto.setMobileNo(mobileNo);
        dto.setPassword(password);
        dto.setRole(role);
        dto.setName(name);
        dto.setCompanyUuid(companyUuid);
        dto.setCompanyCode(companyCode);
        dto.setCreatedBy(createdBy);
        dto.setLocation(location);
        dto.setDateOfBirth(dateOfBirth);
        dto.setJoiningDate(joiningDate);
        dto.setIsActive(isActive);

        User expectedEntity = User.builder()
                .id(id)
                .employeeId(employeeId)
                .email(email)
                .mobileNo(mobileNo)
                .password(password)
                .role(role)
                .name(name)
                .companyUuid(companyUuid)
                .companyCode(companyCode)
                .createdBy(createdBy)
                .location(location)
                .dateOfBirth(dateOfBirth)
                .joiningDate(joiningDate)
                .isActive(isActive)
                .build();

        User entity = mapper.toEntity(dto);
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedEntity);

        OffsetDateTime createdAt = OffsetDateTime.parse("2023-06-01T00:00:00Z");
        OffsetDateTime updatedAt = OffsetDateTime.parse("2023-06-02T00:00:00Z");
        Boolean deleted = true;
        OffsetDateTime deletedAt = OffsetDateTime.parse("2023-06-03T00:00:00Z");
        Long changeId = 25L;

        User entityForDto = User.builder()
                .id(id)
                .employeeId(employeeId)
                .email(email)
                .mobileNo(mobileNo)
                .password(password)
                .role(role)
                .name(name)
                .companyUuid(companyUuid)
                .companyCode(companyCode)
                .createdBy(createdBy)
                .location(location)
                .dateOfBirth(dateOfBirth)
                .joiningDate(joiningDate)
                .isActive(isActive)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deleted(deleted)
                .deletedAt(deletedAt)
                .changeId(changeId)
                .build();

        UserResponseDto expectedDto = new UserResponseDto();
        expectedDto.setId(id);
        expectedDto.setEmployeeId(employeeId);
        expectedDto.setEmail(email);
        expectedDto.setMobileNo(mobileNo);
        expectedDto.setPassword(password);
        expectedDto.setRole(role);
        expectedDto.setName(name);
        expectedDto.setCompanyUuid(companyUuid);
        expectedDto.setCompanyCode(companyCode);
        expectedDto.setCreatedBy(createdBy);
        expectedDto.setLocation(location);
        expectedDto.setDateOfBirth(dateOfBirth);
        expectedDto.setJoiningDate(joiningDate);
        expectedDto.setIsActive(isActive);
        expectedDto.setCreatedAt(createdAt);
        expectedDto.setUpdatedAt(updatedAt);
        expectedDto.setDeleted(deleted);
        expectedDto.setDeletedAt(deletedAt);
        expectedDto.setChangeId(changeId);

        UserResponseDto actualDto = mapper.toDto(entityForDto);
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void nullHandling() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDto(null)).isNull();
    }
}

