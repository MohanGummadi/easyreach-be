package com.easyreach.backend.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CodeGeneratorTest {

    @Test
    void generateUsesProvidedUuidSupplier() {
        UUID fixed = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        CodeGenerator generator = new CodeGenerator(() -> fixed);
        assertThat(generator.generate("C-")).isEqualTo("C-123E4567");
    }
}
