package com.easyreach.tests.auth;

import com.easyreach.tests.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class AuthClient {
    private static String accessToken;

    public static synchronized String getAccessToken() {
        if (accessToken != null) {
            return accessToken;
        }
        Map<String, Object> body = new HashMap<>();
        String email = TestConfig.getAuthEmail();
        String mobile = TestConfig.getAuthMobile();
        String password = TestConfig.getAuthPassword();
        if (email != null && !email.isEmpty()) {
            body.put("email", email);
        } else if (mobile != null && !mobile.isEmpty()) {
            body.put("mobileNo", mobile);
        }
        body.put("password", password);
        Response response = RestAssured.given()
                .baseUri(TestConfig.getBaseUrl())
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth/login");
        accessToken = response.jsonPath().getString("accessToken");
        return accessToken;
    }
}
