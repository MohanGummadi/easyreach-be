package com.easyreach.tests.core;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import com.easyreach.tests.auth.AuthClient;
import com.easyreach.tests.config.TestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseIT {
    protected static RequestSpecification spec;
    private static final OpenApiValidationFilter OAS =
            new OpenApiValidationFilter("src/test/resources/openapi.json");

    @BeforeAll
    static void initSpec() {
        spec = new RequestSpecBuilder()
                .setBaseUri(TestConfig.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + AuthClient.getAccessToken())
                .addFilter(OAS)
                .build();
    }

    protected String pageable() {
        return "?page=0&size=10";
    }

    protected String pageable(String sort) {
        return pageable() + "&sort=" + sort;
    }
}
