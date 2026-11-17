package com.lima.ecommerce.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "A senha deve ter entre 8 e 72 caracteres, contendo pelo menos um letra e um número.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
