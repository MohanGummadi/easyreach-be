package com.easyreach.tests.ledger;

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
public class LedgerIT extends BaseIT {
    private void ensureLedgerData() {
        if (IdStore.get("entryId") == null) {
            String companyId = ensureCompany();
            String payerId = ensurePayer();
            Map<String, Object> entry = SampleData.vehicleEntryRequest(companyId, payerId, "TRUCK");
            Response er = given().spec(spec).body(entry).post("/api/vehicle-entries");
            String entryId = er.jsonPath().getString("data.entryId");
            IdStore.put("entryId", entryId);
        }
    }

    @Test
    @Order(1)
    void shouldGetAllLedgers() {
        ensureLedgerData();
        given().spec(spec).get("/api/ledger" + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(2)
    void shouldGetLedgerForPayer() {
        ensureLedgerData();
        String payerId = IdStore.get("payerId");
        given().spec(spec).get("/api/ledger/" + payerId + pageable())
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(3)
    void shouldGetLedgerSummary() {
        ensureLedgerData();
        given().spec(spec).get("/api/ledger/summary")
                .then().statusCode(200)
                .body("data", notNullValue());
    }

    @Test
    @Order(4)
    void shouldApplyPayment() {
        ensureLedgerData();
        String payerId = IdStore.get("payerId");
        Map<String, Object> body = SampleData.applyPaymentRequest();
        given().spec(spec).body(body).post("/api/ledger/" + payerId + "/apply-payment")
                .then().statusCode(200);
    }
}
