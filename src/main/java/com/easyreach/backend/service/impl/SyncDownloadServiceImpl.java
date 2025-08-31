package com.easyreach.backend.service.impl;

import com.easyreach.backend.service.SyncDownloadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class SyncDownloadServiceImpl implements SyncDownloadService {

    @Override
    public Map<String, Object> downloadChanges(String sinceCursor, List<String> entities, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        if (entities == null) {
            return result;
        }
        for (String entity : entities) {
            result.put(entity, Collections.emptyList());
        }
        return result;
    }
}
