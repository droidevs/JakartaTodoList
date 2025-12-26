/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validators;

import Data.Todo;
import Exceptions.TodoValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

/**
 *
 * @author admin
 */
public class TodoValidator {
    
    private static final Validator validator;
    
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    public static void validate(Todo todo) throws TodoValidationException {
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        
        if (!violations.isEmpty()) {
            throw new TodoValidationException(violations);
        }
    }
}
