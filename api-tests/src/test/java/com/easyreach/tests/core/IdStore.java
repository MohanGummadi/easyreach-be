package com.easyreach.tests.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class IdStore {
    private static final ConcurrentMap<String, String> IDS = new ConcurrentHashMap<>();

    private IdStore() {
    }

    public static void put(String key, String id) {
        IDS.put(key, id);
    }

    public static String get(String key) {
        return IDS.get(key);
    }
}
