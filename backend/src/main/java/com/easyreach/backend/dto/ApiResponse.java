package com.easyreach.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(example = "[]")
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
