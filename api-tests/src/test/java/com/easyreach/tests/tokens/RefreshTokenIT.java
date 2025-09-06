package com.easyreach.tests.tokens;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
import static com.easyreach.tests.core.EntityHelper.ensureUser;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@TestMethodOrder(OrderAnnotation.class)
public class RefreshTokenIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateRefreshToken() {
        String userId = ensureUser();
        Map<String, Object> body = SampleData.refreshTokenRequest(userId);
        Response r = given().spec(spec).body(body).post("/api/refresh-token");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.jti");
        IdStore.put("refreshTokenId", id);
    }

    @Test
    @Order(2)
    void shouldGetRefreshToken() {
        String id = ensureRefreshToken();
        given().spec(spec).get("/api/refresh-token/" + id)
                .then().statusCode(200).body("data.jti", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListRefreshTokens() {
        given().spec(spec).get("/api/refresh-token" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateRefreshToken() {
        String id = ensureRefreshToken();
        String userId = ensureUser();
        Map<String, Object> body = SampleData.refreshTokenRequest(userId);
        body.put("jti", id);
        given().spec(spec).body(body).put("/api/refresh-token/" + id)
                .then().statusCode(200)
                .body("data.jti", equalTo(id));
    }

    @Test
    @Order(5)
    void shouldDeleteRefreshToken() {
        String id = ensureRefreshToken();
        given().spec(spec).delete("/api/refresh-token/" + id)
                .then().statusCode(200);
    }

    private String ensureRefreshToken() {
        String id = IdStore.get("refreshTokenId");
        if (id == null) {
            String userId = ensureUser();
            Map<String, Object> body = SampleData.refreshTokenRequest(userId);
            Response r = given().spec(spec).body(body).post("/api/refresh-token");
            id = r.jsonPath().getString("data.jti");
            IdStore.put("refreshTokenId", id);
        }
        return id;
    }
}
