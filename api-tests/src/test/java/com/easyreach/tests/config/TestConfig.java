package com.easyreach.tests.config;

public class TestConfig {
    private static final String BASE_URL_DEFAULT = "http://localhost:8080";

    public static String getBaseUrl() {
        return System.getProperty("BASE_URL", System.getenv().getOrDefault("BASE_URL", BASE_URL_DEFAULT));
    }

    public static String getAuthEmail() {
        return System.getProperty("AUTH_EMAIL", System.getenv().get("AUTH_EMAIL"));
    }

    public static String getAuthMobile() {
        return System.getProperty("AUTH_MOBILE", System.getenv().get("AUTH_MOBILE"));
    }

    public static String getAuthPassword() {
        return System.getProperty("AUTH_PASSWORD", System.getenv().getOrDefault("AUTH_PASSWORD", ""));
    }
}
