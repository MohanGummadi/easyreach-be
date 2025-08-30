package com.easyreach.backend.auth.controller;

import com.easyreach.backend.auth.dto.UserDto;
import com.easyreach.backend.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable String id) {
        return service.get(id);
    }

    @GetMapping
    public Page<UserDto> list(Pageable pageable) {
        return service.list(pageable);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable String id, @RequestBody UserDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
