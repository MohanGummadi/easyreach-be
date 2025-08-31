package com.easyreach.backend.dto.users;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
  @NotBlank
  private String id;
  @NotBlank
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
  @NotNull
  private Boolean isActive;
  @NotNull
  private OffsetDateTime createdAt;
  @NotNull
  private OffsetDateTime updatedAt;

  @NotNull
  private Boolean deleted;

  private OffsetDateTime deletedAt;

  private Long changeId;
}
