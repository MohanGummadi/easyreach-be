package com.easyreach.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private List<String> errors = new ArrayList<>();

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, new ArrayList<>());
    }

    public static <T> ApiResponse<T> failure(List<String> errors) {
        return new ApiResponse<>(false, null, errors);
    }

    public static <T> ApiResponse<T> failure(String error) {
        List<String> errs = new ArrayList<>();
        errs.add(error);
        return new ApiResponse<>(false, null, errs);
    }
}
