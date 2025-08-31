package com.easyreach.backend.dto.users;

import lombok.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private String id;
  private String employeeId;
  private String email;
  private String mobileNo;
  private String password;
  private String role;
  private String name;
  private String companyUuid;
  private String companyCode;
  private String createdBy;
  private String location;
  private LocalDate dateOfBirth;
  private LocalDate joiningDate;
  private Boolean isActive;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

  private Boolean deleted;
  private OffsetDateTime deletedAt;
  private Long changeId;
}
