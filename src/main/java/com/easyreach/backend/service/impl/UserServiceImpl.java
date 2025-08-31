package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;
import com.easyreach.backend.entity.User;
import com.easyreach.backend.mapper.UserMapper;
import com.easyreach.backend.repository.UserRepository;
import com.easyreach.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public ApiResponse<UserResponseDto> create(UserRequestDto dto) {
        User entity = mapper.toEntity(dto);
        return ApiResponse.success(mapper.toDto(repository.save(entity)));
    }

    @Override
    public ApiResponse<UserResponseDto> update(String id, UserRequestDto dto) {
        User e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        mapper.update(e, dto);
        return ApiResponse.success(mapper.toDto(repository.save(e)));
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("User not found: " + id);
        repository.deleteById(id);
        return ApiResponse.success(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<UserResponseDto> get(String id) {
        User e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        return ApiResponse.success(mapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Page<UserResponseDto>> list(Pageable pageable) {
        return ApiResponse.success(repository.findAll(pageable).map(mapper::toDto));
    }
}
