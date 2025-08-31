package com.easyreach.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private String id;
  private String uuid;
  private String employeeId;
  private String email;
  private String mobileNo;
  private String password;
  private String role;
  private String name;
  private String companyId;
  private String companyName;
  private String createdBy;
  private String location;
  private String dateOfBirth;
  private String joiningDate;
  private Integer isActive;
}
