/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validators;


import Exceptions.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 *
 * @author admin
 */
public final class RequestValidator {

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private RequestValidator() {
        // prevent instantiation
    }

    public static <T> void validate(T request) throws ValidationException{
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Set<ConstraintViolation<T>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
    }
}

