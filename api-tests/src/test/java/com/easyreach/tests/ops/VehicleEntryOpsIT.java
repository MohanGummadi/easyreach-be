package com.easyreach.tests.ops;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import com.easyreach.tests.core.SampleData;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Instant;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@TestMethodOrder(OrderAnnotation.class)
public class VehicleEntryOpsIT extends BaseIT {
    private String ensureEntry() {
        String id = IdStore.get("entryId");
        if (id == null) {
            // create dependencies
            String companyId = IdStore.get("companyUuid");
            if (companyId == null) {
                Map<String, Object> company = SampleData.companyRequest();
                Response cr = given().spec(spec).body(company).post("/api/companies");
                companyId = cr.jsonPath().getString("data.uuid");
                IdStore.put("companyUuid", companyId);
            }
            String payerId = IdStore.get("payerId");
            if (payerId == null) {
                Map<String, Object> payer = SampleData.payerRequest(companyId);
                Response pr = given().spec(spec).body(payer).post("/api/payers");
                payerId = pr.jsonPath().getString("data.payerId");
                IdStore.put("payerId", payerId);
            }
            Map<String, Object> entry = SampleData.vehicleEntryRequest(companyId, payerId, "TRUCK");
            Response er = given().spec(spec).body(entry).post("/api/vehicle-entries");
            id = er.jsonPath().getString("data.entryId");
            IdStore.put("entryId", id);
        }
        return id;
    }

    @Test
    @Order(1)
    void shouldAddPayment() {
        String entryId = ensureEntry();
        Map<String, Object> body = SampleData.addPaymentRequest();
        given().spec(spec).body(body).post("/api/vehicle-entries-ops/" + entryId + "/payment")
                .then().statusCode(200)
                .body("data.entryId", equalTo(entryId));
    }

    @Test
    @Order(2)
    void shouldExitVehicle() {
        String entryId = ensureEntry();
        String when = Instant.now().toString();
        given().spec(spec).post("/api/vehicle-entries-ops/" + entryId + "/exit?when=" + when)
                .then().statusCode(200)
                .body("data.entryId", equalTo(entryId));
    }
}
