package com.easyreach.backend.auth.dto;

import com.easyreach.backend.auth.entity.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private String id;
    private String uuid;
    private String employeeId;
    private String email;
    private String mobileNo;
    private String role;
    private String name;
    private String companyId;
    private String companyName;
    private String createdBy;
    private String location;
    private LocalDate dateOfBirth;
    private LocalDate joiningDate;
    private Integer isActive;
}
