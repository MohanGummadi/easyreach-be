package com.easyreach.tests.receipts;

import com.easyreach.tests.core.BaseIT;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
@TestMethodOrder(OrderAnnotation.class)
public class ReceiptIT extends BaseIT {
    @Test
    @Order(1)
    void shouldGetReceiptByOrder() {
        given().spec(spec).get("/api/receipts/order/unknown")
                .then().statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(2)
    void shouldGeneratePdf() {
        given().spec(spec)
                .multiPart("data", "{\"id\":1}")
                .post("/api/receipts/pdf")
                .then().statusCode(200);
    }
}
