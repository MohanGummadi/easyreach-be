package com.easyreach.tests.core;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class EntityHelper {
    public static String ensureCompany() {
        String id = IdStore.get("companyUuid");
        if (id == null) {
            Map<String, Object> body = SampleData.companyRequest();
            Response r = given().spec(BaseIT.spec).body(body).post("/api/companies");
            id = r.jsonPath().getString("data.uuid");
            IdStore.put("companyUuid", id);
        }
        return id;
    }

    public static String ensurePayer() {
        String id = IdStore.get("payerId");
        if (id == null) {
            String companyId = ensureCompany();
            Map<String, Object> body = SampleData.payerRequest(companyId);
            Response r = given().spec(BaseIT.spec).body(body).post("/api/payers");
            id = r.jsonPath().getString("data.payerId");
            IdStore.put("payerId", id);
        }
        return id;
    }

    public static String ensureUser() {
        String id = IdStore.get("userId");
        if (id == null) {
            String companyId = ensureCompany();
            Map<String, Object> body = SampleData.userRequest(companyId);
            Response r = given().spec(BaseIT.spec).body(body).post("/api/users");
            id = r.jsonPath().getString("data.id");
            IdStore.put("userId", id);
        }
        return id;
    }
}
