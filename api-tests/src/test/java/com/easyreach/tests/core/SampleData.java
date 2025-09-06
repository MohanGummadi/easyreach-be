package com.easyreach.tests.core;

import net.datafaker.Faker;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SampleData {
    private static final Faker FAKER = new Faker();

    private SampleData() {
    }

    public static Map<String, Object> company() {
        Map<String, Object> m = new HashMap<>();
        m.put("name", FAKER.company().name());
        m.put("address", FAKER.address().fullAddress());
        return m;
    }

    public static Map<String, Object> vehicleType() {
        Map<String, Object> m = new HashMap<>();
        m.put("name", FAKER.rockBand().name());
        return m;
    }

    public static Map<String, Object> internalVehicle() {
        Map<String, Object> m = new HashMap<>();
        m.put("vehicleNumber", "V-" + FAKER.number().digits(5));
        m.put("type", vehicleType());
        return m;
    }

    public static Map<String, Object> vehicleEntry() {
        Map<String, Object> m = new HashMap<>();
        m.put("vehicleNumber", "E-" + FAKER.number().digits(5));
        m.put("enteredAt", Instant.now().toString());
        return m;
    }

    public static Map<String, Object> dailyExpense() {
        Map<String, Object> m = new HashMap<>();
        m.put("expenseDate", LocalDate.now().toString());
        m.put("amount", FAKER.number().randomDouble(2, 10, 100));
        m.put("description", FAKER.lorem().sentence());
        return m;
    }

    public static Map<String, Object> expenseMaster() {
        Map<String, Object> m = new HashMap<>();
        m.put("name", FAKER.commerce().productName());
        return m;
    }

    public static Map<String, Object> dieselUsage() {
        Map<String, Object> m = new HashMap<>();
        m.put("quantity", FAKER.number().numberBetween(1, 100));
        m.put("usedAt", Instant.now().toString());
        return m;
    }

    public static Map<String, Object> equipmentUsage() {
        Map<String, Object> m = new HashMap<>();
        m.put("equipment", FAKER.commerce().material());
        m.put("usedAt", Instant.now().toString());
        return m;
    }

    public static Map<String, Object> payer() {
        Map<String, Object> m = new HashMap<>();
        m.put("name", FAKER.name().fullName());
        return m;
    }

    public static Map<String, Object> payerSettlement() {
        Map<String, Object> m = new HashMap<>();
        m.put("settledAt", Instant.now().toString());
        m.put("amount", FAKER.number().randomDouble(2, 10, 100));
        return m;
    }

    public static Map<String, Object> ledgerPayment() {
        Map<String, Object> m = new HashMap<>();
        m.put("paymentDate", LocalDate.now().toString());
        m.put("amount", FAKER.number().randomDouble(2, 10, 100));
        m.put("reference", UUID.randomUUID().toString());
        return m;
    }
}
