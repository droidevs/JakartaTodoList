/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

import Data.Todo;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

/**
 *
 * @author admin
 */
@Deprecated
public class TodoValidationException extends RuntimeException {
    
    
    private final Set<ConstraintViolation<Todo>> violations;

    public TodoValidationException(Set<ConstraintViolation<Todo>> violations) {
        this.violations = violations;
    }

    public Set<ConstraintViolation<Todo>> getViolations() {
        return violations;
    }
    
}
