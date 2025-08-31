package com.easyreach.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TombstoneDto {
    private String id;
    private OffsetDateTime deletedAt;
}
