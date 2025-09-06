package com.easyreach.tests.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IdStore {
    private static final Map<String, String> STORE = new ConcurrentHashMap<>();

    public static void put(String key, String value) {
        STORE.put(key, value);
    }

    public static String get(String key) {
        return STORE.get(key);
    }
}
