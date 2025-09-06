package com.easyreach.backend.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    void generateUsesPrefixAndUpperCaseRandomSection() {
        UUID fixed = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        CodeGenerator generator = new CodeGenerator(() -> fixed);

        String result = generator.generate("PRE-");

        assertEquals("PRE-123E4567", result);
        assertEquals(12, result.length());
    }

    @Test
    void generateHandlesEmptyPrefix() {
        UUID fixed = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        CodeGenerator generator = new CodeGenerator(() -> fixed);

        String result = generator.generate("");

        assertEquals("123E4567", result);
    }

    @Test
    void generateProducesDifferentValuesForDifferentUuids() {
        UUID first = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID second = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");
        AtomicInteger counter = new AtomicInteger();
        Supplier<UUID> supplier = () -> counter.getAndIncrement() == 0 ? first : second;
        CodeGenerator generator = new CodeGenerator(supplier);

        String one = generator.generate("X");
        String two = generator.generate("X");

        assertNotEquals(one, two);
    }
}

