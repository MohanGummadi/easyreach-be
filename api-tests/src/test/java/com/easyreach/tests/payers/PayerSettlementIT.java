package com.easyreach.tests.payers;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
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
public class PayerSettlementIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreatePayerSettlement() {
        String companyId = ensureCompany();
        String payerId = ensurePayer();
        Map<String, Object> body = SampleData.payerSettlementRequest(companyId, payerId);
        Response r = given().spec(spec).body(body).post("/api/payer-settlements");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.settlementId");
        IdStore.put("settlementId", id);
    }

    @Test
    @Order(2)
    void shouldGetPayerSettlement() {
        String id = IdStore.get("settlementId");
        given().spec(spec).get("/api/payer-settlements/" + id)
                .then().statusCode(200).body("data.settlementId", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListPayerSettlements() {
        given().spec(spec).get("/api/payer-settlements" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdatePayerSettlement() {
        String id = IdStore.get("settlementId");
        String companyId = IdStore.get("companyUuid");
        String payerId = IdStore.get("payerId");
        Map<String, Object> body = SampleData.payerSettlementRequest(companyId, payerId);
        body.put("settlementId", id);
        body.put("amount", 200);
        given().spec(spec).body(body).put("/api/payer-settlements/" + id)
                .then().statusCode(200)
                .body("data.amount", equalTo(200));
    }

    @Test
    @Order(5)
    void shouldGetByPayerAndCompany() {
        String payerId = IdStore.get("payerId");
        String companyId = IdStore.get("companyUuid");
        given().spec(spec).get("/api/payer-settlements/payer/" + payerId)
                .then().statusCode(200).body("data", notNullValue());
        given().spec(spec).get("/api/payer-settlements/company/" + companyId)
                .then().statusCode(200).body("data", notNullValue());
    }

    @Test
    @Order(6)
    void shouldDeletePayerSettlement() {
        String id = IdStore.get("settlementId");
        given().spec(spec).delete("/api/payer-settlements/" + id)
                .then().statusCode(200);
    }

    private String ensureCompany() {
        String id = IdStore.get("companyUuid");
        if (id == null) {
            Map<String, Object> body = SampleData.companyRequest();
            Response r = given().spec(spec).body(body).post("/api/companies");
            id = r.jsonPath().getString("data.uuid");
            IdStore.put("companyUuid", id);
        }
        return id;
    }

    private String ensurePayer() {
        String id = IdStore.get("payerId");
        if (id == null) {
            String companyId = ensureCompany();
            Map<String, Object> body = SampleData.payerRequest(companyId);
            Response r = given().spec(spec).body(body).post("/api/payers");
            id = r.jsonPath().getString("data.payerId");
            IdStore.put("payerId", id);
        }
        return id;
    }
}
