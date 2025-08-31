package com.easyreach.backend.mapper;

import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;
import com.easyreach.backend.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-31T09:49:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.getId() );
        user.employeeId( dto.getEmployeeId() );
        user.email( dto.getEmail() );
        user.mobileNo( dto.getMobileNo() );
        user.password( dto.getPassword() );
        user.role( dto.getRole() );
        user.name( dto.getName() );
        user.companyUuid( dto.getCompanyUuid() );
        user.companyCode( dto.getCompanyCode() );
        user.createdBy( dto.getCreatedBy() );
        user.location( dto.getLocation() );
        user.dateOfBirth( dto.getDateOfBirth() );
        user.joiningDate( dto.getJoiningDate() );
        user.isActive( dto.getIsActive() );
        user.createdAt( dto.getCreatedAt() );
        user.updatedAt( dto.getUpdatedAt() );

        return user.build();
    }

    @Override
    public UserResponseDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId( entity.getId() );
        userResponseDto.setEmployeeId( entity.getEmployeeId() );
        userResponseDto.setEmail( entity.getEmail() );
        userResponseDto.setMobileNo( entity.getMobileNo() );
        userResponseDto.setPassword( entity.getPassword() );
        userResponseDto.setRole( entity.getRole() );
        userResponseDto.setName( entity.getName() );
        userResponseDto.setCompanyUuid( entity.getCompanyUuid() );
        userResponseDto.setCompanyCode( entity.getCompanyCode() );
        userResponseDto.setCreatedBy( entity.getCreatedBy() );
        userResponseDto.setLocation( entity.getLocation() );
        userResponseDto.setDateOfBirth( entity.getDateOfBirth() );
        userResponseDto.setJoiningDate( entity.getJoiningDate() );
        userResponseDto.setIsActive( entity.getIsActive() );
        userResponseDto.setCreatedAt( entity.getCreatedAt() );
        userResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return userResponseDto;
    }

    @Override
    public void update(User entity, UserRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getEmployeeId() != null ) {
            entity.setEmployeeId( dto.getEmployeeId() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getMobileNo() != null ) {
            entity.setMobileNo( dto.getMobileNo() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getRole() != null ) {
            entity.setRole( dto.getRole() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getCompanyUuid() != null ) {
            entity.setCompanyUuid( dto.getCompanyUuid() );
        }
        if ( dto.getCompanyCode() != null ) {
            entity.setCompanyCode( dto.getCompanyCode() );
        }
        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getLocation() != null ) {
            entity.setLocation( dto.getLocation() );
        }
        if ( dto.getDateOfBirth() != null ) {
            entity.setDateOfBirth( dto.getDateOfBirth() );
        }
        if ( dto.getJoiningDate() != null ) {
            entity.setJoiningDate( dto.getJoiningDate() );
        }
        if ( dto.getIsActive() != null ) {
            entity.setIsActive( dto.getIsActive() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
    }
}
