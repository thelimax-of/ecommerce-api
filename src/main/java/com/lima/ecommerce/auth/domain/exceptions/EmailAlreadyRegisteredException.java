package com.lima.ecommerce.auth.domain.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String email) {
        super("E-mail já cadastrado: " + email);
    }
}
