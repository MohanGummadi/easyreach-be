package com.easyreach.tests.ops;

import com.easyreach.tests.core.BaseIT;
import com.easyreach.tests.core.IdStore;
import static com.easyreach.tests.core.EntityHelper.ensurePayer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@TestMethodOrder(OrderAnnotation.class)
public class PayerOpsIT extends BaseIT {
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
