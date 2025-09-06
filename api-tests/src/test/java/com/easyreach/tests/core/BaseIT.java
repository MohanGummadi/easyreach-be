package com.easyreach.tests.core;

import com.easyreach.tests.auth.AuthClient;
import com.easyreach.tests.config.TestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseIT {
    protected static RequestSpecification spec;

    @BeforeAll
    static void initSpec() {
        spec = new RequestSpecBuilder()
                .setBaseUri(TestConfig.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + AuthClient.getAccessToken())
                .build();
    }

    protected String pageable() {
        return "?page=0&size=10";
    }

    protected String pageable(String sort) {
        return pageable() + "&sort=" + sort;
    }
}
