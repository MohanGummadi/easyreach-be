package com.easyreach.tests.config;

public final class TestConfig {
    private TestConfig() {
    }

    private static String get(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value;
    }

    private static String get(String key, String def) {
        String value = get(key);
        return value == null ? def : value;
    }

    public static String getBaseUrl() {
        return get("BASE_URL", "http://localhost:8080");
    }

    public static String getAuthEmail() {
        return get("AUTH_EMAIL");
    }

    public static String getAuthMobile() {
        return get("AUTH_MOBILE");
    }

    public static String getAuthPassword() {
        return get("AUTH_PASSWORD", "admin");
    }
}
