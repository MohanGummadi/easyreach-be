package com.easyreach.backend.service;

import com.easyreach.backend.dto.SyncDownloadResponseDto;

import java.time.OffsetDateTime;

public interface SyncService {
    SyncDownloadResponseDto download(OffsetDateTime cursor, int limit);
}
