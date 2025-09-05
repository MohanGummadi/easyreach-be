package com.easyreach.backend.util;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Simple code generator utility used to create identifiers for various entities.
 */
@Component
public class CodeGenerator {

    private final Supplier<UUID> uuidSupplier;

    public CodeGenerator() {
        this(UUID::randomUUID);
    }

    // Visible for testing
    CodeGenerator(Supplier<UUID> uuidSupplier) {
        this.uuidSupplier = uuidSupplier;
    }

    /**
     * Generates a random code prefixed with the supplied value. The resulting
     * code is upper case and stripped of dashes to fit within typical identifier
     * length constraints.
     *
     * @param prefix value to prefix the generated code with
     * @return generated unique code
     */
    public String generate(String prefix) {
        String random = uuidSupplier.get().toString().replace("-", "").substring(0, 8).toUpperCase();
        return prefix + random;
    }
}
