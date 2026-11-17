package com.lima.ecommerce.shared.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StrongPasswordTest {

    private final StrongPasswordValidator validator = new StrongPasswordValidator();

    @ParameterizedTest
    @ValueSource(strings = {
            "abc123",          // menor que 8 chars
            "somenteletras",   // sem números
            "12345678",        // sem letras
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1" // 73 chars
    })
    void shouldRejectWeakPasswords(String password) {
        assertFalse(validator.isValid(password, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "segura123",
            "SenhaForte99"
    })
    void shouldAcceptStrongPasswords(String password) {
        assertTrue(validator.isValid(password, null));
    }
}