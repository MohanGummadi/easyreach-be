package com.easyreach.backend.auth.service;

import com.easyreach.backend.auth.dto.UserDto;
import com.easyreach.backend.auth.entity.User;
import com.easyreach.backend.auth.mapper.UserMapper;
import com.easyreach.backend.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserDto create(UserDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("id is required");
        }
        if (dto.getIsActive() == null) {
            dto.setIsActive(1);
        }
        User entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public UserDto get(String id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public Page<UserDto> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public UserDto update(String id, UserDto dto) {
        Optional<User> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }
        User existing = existingOpt.get();
        existing.setEmployeeId(dto.getEmployeeId());
        existing.setEmail(dto.getEmail());
        existing.setMobileNo(dto.getMobileNo());
        existing.setRole(dto.getRole());
        existing.setName(dto.getName());
        existing.setCompanyId(dto.getCompanyId());
        existing.setCompanyName(dto.getCompanyName());
        existing.setCreatedBy(dto.getCreatedBy());
        existing.setLocation(dto.getLocation());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setJoiningDate(dto.getJoiningDate());
        existing.setIsActive(dto.getIsActive() == null ? 1 : dto.getIsActive());
        return mapper.toDto(repository.save(existing));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
