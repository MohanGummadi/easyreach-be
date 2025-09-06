package com.easyreach.tests.core;

import com.easyreach.tests.auth.AuthClient;
import com.easyreach.tests.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseIT {
    public static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeAll
    public static void setupRestAssured() {
        RestAssured.baseURI = TestConfig.getBaseUrl();
        RestAssured.config = RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2));
    }

    protected RequestSpecification givenAuth() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + AuthClient.getToken());
    }

    protected Map<String, Object> pageable() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 20);
        return params;
    }
}
