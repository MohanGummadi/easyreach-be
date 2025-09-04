package com.easyreach.backend.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Simple code generator utility used to create identifiers for various entities.
 */
@Component
public class CodeGenerator {

    /**
     * Generates a random code prefixed with the supplied value. The resulting
     * code is upper case and stripped of dashes to fit within typical identifier
     * length constraints.
     *
     * @param prefix value to prefix the generated code with
     * @return generated unique code
     */
    public String generate(String prefix) {
        String random = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return prefix + random;
    }
}
