package com.easyreach.tests.expenses;

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
public class DailyExpenseIT extends BaseIT {
    @Test
    @Order(1)
    void shouldCreateDailyExpense() {
        String companyId = ensureCompany();
        Map<String, Object> body = SampleData.dailyExpenseRequest(companyId);
        Response r = given().spec(spec).body(body).post("/api/daily-expenses");
        r.then().statusCode(200);
        String id = r.jsonPath().getString("data.expenseId");
        IdStore.put("dailyExpenseId", id);
    }

    @Test
    @Order(2)
    void shouldGetDailyExpense() {
        String id = IdStore.get("dailyExpenseId");
        given().spec(spec).get("/api/daily-expenses/" + id)
                .then().statusCode(200).body("data.expenseId", equalTo(id));
    }

    @Test
    @Order(3)
    void shouldListDailyExpenses() {
        String from = Instant.now().minusSeconds(3600).toString();
        String to = Instant.now().toString();
        given().spec(spec).get("/api/daily-expenses" + pageable() + "&dateFrom=" + from + "&dateTo=" + to)
                .then().statusCode(200)
                .body("data.content", notNullValue());
    }

    @Test
    @Order(4)
    void shouldUpdateDailyExpense() {
        String id = IdStore.get("dailyExpenseId");
        String companyId = IdStore.get("companyUuid");
        Map<String, Object> body = SampleData.dailyExpenseRequest(companyId);
        body.put("expenseId", id);
        body.put("expenseAmount", 200);
        given().spec(spec).body(body).put("/api/daily-expenses/" + id)
                .then().statusCode(200)
                .body("data.expenseAmount", equalTo(200));
    }

    @Test
    @Order(5)
    void shouldDeleteDailyExpense() {
        String id = IdStore.get("dailyExpenseId");
        given().spec(spec).delete("/api/daily-expenses/" + id)
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
}
