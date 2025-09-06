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

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@TestMethodOrder(OrderAnnotation.class)
public class PayerOpsIT extends BaseIT {
    private void ensurePayer() {
        if (IdStore.get("payerId") == null) {
            String companyId = IdStore.get("companyUuid");
            if (companyId == null) {
                Map<String, Object> company = SampleData.companyRequest();
                Response cr = given().spec(spec).body(company).post("/api/companies");
                companyId = cr.jsonPath().getString("data.uuid");
                IdStore.put("companyUuid", companyId);
            }
            Map<String, Object> payer = SampleData.payerRequest(companyId);
            Response pr = given().spec(spec).body(payer).post("/api/payers");
            String payerId = pr.jsonPath().getString("data.payerId");
            IdStore.put("payerId", payerId);
        }
    }

    @Test
    @Order(1)
    void shouldSearchPayers() {
        ensurePayer();
        String companyId = IdStore.get("companyUuid");
        given().spec(spec).get("/api/payers-ops/search?companyUuid=" + companyId + "&q=&page=0&size=10")
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(2)
    void shouldSoftDeletePayer() {
        ensurePayer();
        String payerId = IdStore.get("payerId");
        given().spec(spec).delete("/api/payers-ops/" + payerId + "/soft")
                .then().statusCode(200);
    }
}
