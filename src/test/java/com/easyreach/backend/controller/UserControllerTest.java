package com.easyreach.backend.controller;

import com.easyreach.backend.dto.ApiResponse;
import com.easyreach.backend.dto.users.UserRequestDto;
import com.easyreach.backend.dto.users.UserResponseDto;
import com.easyreach.backend.exception.GlobalExceptionHandler;
import com.easyreach.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("create user – success")
    void createUserSuccess() throws Exception {
        UserResponseDto resp = new UserResponseDto();
        resp.setId("u1");
        when(userService.create(any(UserRequestDto.class)))
                .thenReturn(ApiResponse.success(resp));

        String body = "{\"id\":\"u1\",\"employeeId\":\"e1\",\"isActive\":true}";
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value("u1"));

        verify(userService).create(any(UserRequestDto.class));
    }

    @Test
    @DisplayName("get user – not found")
    void getUserNotFound() throws Exception {
        when(userService.get(eq("missing")))
                .thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("User not found"));

        verify(userService).get("missing");
    }

    @Test
    @DisplayName("create user – bad request")
    void createUserBadRequest() throws Exception {
        when(userService.create(any(UserRequestDto.class)))
                .thenThrow(new IllegalArgumentException("Bad payload"));
        String body = "{\"id\":\"u1\",\"employeeId\":\"e1\",\"isActive\":true}";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Bad payload"));
    }

    @Test
    @DisplayName("get user – server error")
    void getUserServerError() throws Exception {
        when(userService.get(eq("u1")))
                .thenThrow(new RuntimeException("boom"));

        mockMvc.perform(get("/api/users/u1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: boom"));
    }
}

