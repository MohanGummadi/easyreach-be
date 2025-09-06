package com.easyreach.backend.service;

import java.util.List;
import java.util.Map;

public interface SyncDownloadService {
    Map<String, Object> downloadChanges(String sinceCursor, List<String> entities, Integer limit);
}
