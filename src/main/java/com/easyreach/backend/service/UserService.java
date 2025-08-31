package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    ApiResponse<UserResponseDto> create(UserRequestDto dto);
    ApiResponse<UserResponseDto> update(String id, UserRequestDto dto);
    ApiResponse<Void> delete(String id);
    ApiResponse<UserResponseDto> get(String id);
    ApiResponse<Page<UserResponseDto>> list(Pageable pageable);
}
