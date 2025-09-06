package com.easyreach.backend.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void successFactoryPopulatesFields() {
        ApiResponse<String> response = ApiResponse.success("data");
        assertTrue(response.isSuccess());
        assertEquals("data", response.getData());
        assertNotNull(response.getErrors());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    void failureWithListFactoryPopulatesErrors() {
        List<String> errors = List.of("e1", "e2");
        ApiResponse<Object> response = ApiResponse.failure(errors);
        assertFalse(response.isSuccess());
        assertNull(response.getData());
        assertEquals(errors, response.getErrors());
    }

    @Test
    void failureWithSingleErrorFactoryCreatesList() {
        ApiResponse<Object> response = ApiResponse.failure("boom");
        assertFalse(response.isSuccess());
        assertNull(response.getData());
        assertEquals(List.of("boom"), response.getErrors());
    }
}

