package com.easyreach.tests.auth;

import com.easyreach.tests.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public final class AuthClient {
    private static String token;

    private AuthClient() {
    }

    public static synchronized String getToken() {
        if (token == null) {
            Map<String, Object> body = new HashMap<>();
            if (TestConfig.getAuthEmail() != null) {
                body.put("email", TestConfig.getAuthEmail());
            }
            if (TestConfig.getAuthMobile() != null) {
                body.put("mobile", TestConfig.getAuthMobile());
            }
            body.put("password", TestConfig.getAuthPassword());

            token = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(TestConfig.getBaseUrl() + "/auth/login")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("accessToken");
        }
        return token;
    }
}
