package com.easyreach.backend.service.impl;

import com.easyreach.backend.dto.SyncDownloadResponseDto;
import com.easyreach.backend.service.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SyncServiceImpl implements SyncService {
    @Override
    public SyncDownloadResponseDto download(OffsetDateTime cursor, int limit) {
        SyncDownloadResponseDto response = new SyncDownloadResponseDto();
        response.setItems(Collections.emptyMap());
        response.setTombstones(Collections.emptyMap());
        response.setCursorEnd(cursor);
        response.setHasMore(false);
        return response;
    }
}
