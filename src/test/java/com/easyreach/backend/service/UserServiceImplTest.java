package com.easyreach.backend.service;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;
import com.easyreach.backend.entity.User;
import com.easyreach.backend.mapper.UserMapper;
import com.easyreach.backend.repository.UserRepository;
import com.easyreach.backend.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl service;

    private User entity;
    private UserRequestDto request;
    private UserResponseDto response;

    @BeforeEach
    void init() {
        entity = new User();
        request = new UserRequestDto();
        response = new UserResponseDto();
    }

    @Test
    void create_savesEntity() {
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<UserResponseDto> result = service.create(request);

        assertTrue(result.isSuccess());
        verify(repository).save(entity);
    }

    @Test
    void get_returnsDto() {
        when(repository.findById("1")).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<UserResponseDto> result = service.get("1");

        assertTrue(result.isSuccess());
        verify(repository).findById("1");
    }

    @Test
    void get_notFoundThrows() {
        when(repository.findById("1")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.get("1"));
    }

    @Test
    void list_usesRepository() {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDto(entity)).thenReturn(response);

        ApiResponse<?> result = service.list(Pageable.unpaged());
        assertTrue(result.isSuccess());
        verify(repository).findAll(any(Pageable.class));
    }
}
