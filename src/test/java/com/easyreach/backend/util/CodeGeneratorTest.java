package com.easyreach.backend.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeGeneratorTest {

    private final CodeGenerator generator = new CodeGenerator();

    @Test
    void generateAddsPrefixAndUppercase() {
        String code = generator.generate("C-");
        assertThat(code).startsWith("C-");
        assertThat(code.length()).isEqualTo(10); // prefix (2) + 8 random chars
        assertThat(code.substring(2)).matches("[A-Z0-9]{8}");
    }
}
