package com.easyreach.backend.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
public class SyncDownloadResponseDto {
    private Map<String, List<?>> items;
    private Map<String, List<TombstoneDto>> tombstones;
    private OffsetDateTime cursorEnd;
    private boolean hasMore;
}
