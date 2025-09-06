package com.easyreach.tests.payers;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
import static com.easyreach.tests.core.EntityHelper.ensureCompany;
import static com.easyreach.tests.core.EntityHelper.ensurePayer;
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
        String id = ensurePayerSettlement();
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
        String id = ensurePayerSettlement();
        String companyId = ensureCompany();
        String payerId = ensurePayer();
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
        ensurePayerSettlement();
        String payerId = ensurePayer();
        String companyId = ensureCompany();
        given().spec(spec).get("/api/payer-settlements/payer/" + payerId)
                .then().statusCode(200).body("data", notNullValue());
        given().spec(spec).get("/api/payer-settlements/company/" + companyId)
                .then().statusCode(200).body("data", notNullValue());
    }

    @Test
    @Order(6)
    void shouldDeletePayerSettlement() {
        String id = ensurePayerSettlement();
        given().spec(spec).delete("/api/payer-settlements/" + id)
                .then().statusCode(200);
    }

    private String ensurePayerSettlement() {
        String id = IdStore.get("settlementId");
        if (id == null) {
            String companyId = ensureCompany();
            String payerId = ensurePayer();
            Map<String, Object> body = SampleData.payerSettlementRequest(companyId, payerId);
            Response r = given().spec(spec).body(body).post("/api/payer-settlements");
            id = r.jsonPath().getString("data.settlementId");
            IdStore.put("settlementId", id);
        }
        return id;
    }
}
