package com.easyreach.tests.sync;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.SampleData;
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
public class SyncIT extends BaseIT {
    private Map<String, Object> buildSyncRequest() {
        Map<String, Object> company = SampleData.companyRequest();
        Map<String, Object> payer = SampleData.payerRequest(company.get("uuid").toString());
        Map<String, Object> vehicleType = SampleData.vehicleTypeRequest(company.get("uuid").toString());
        Map<String, Object> entry = SampleData.vehicleEntryRequest(company.get("uuid").toString(),
                payer.get("payerId").toString(), vehicleType.get("vehicleType").toString());
        return SampleData.syncRequest(company, payer, vehicleType, entry);
    }

    @Test
    @Order(1)
    void shouldSyncEntities() {
        Map<String, Object> body = buildSyncRequest();
        given().spec(spec).body(body).post("/api/sync")
                .then().statusCode(200)
                .body("data", notNullValue());
    }

    @Test
    @Order(2)
    void shouldDownloadChanges() {
        given().spec(spec).get("/api/sync/download?limit=10")
                .then().statusCode(200)
                .body("data", notNullValue());
    }
}
