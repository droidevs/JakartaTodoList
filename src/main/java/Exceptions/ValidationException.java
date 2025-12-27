/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

import static Validators.ValidationUtils.toMessageString;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

/**
 *
 * @author admin
 * @param <?>
 */
public class ValidationException extends Exception {

    private final Set<? extends ConstraintViolation<?>> violations;

    public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
        super("Validation failed");
        this.violations = violations;
    }

    public Set<? extends ConstraintViolation<?>> getViolations() {
        return violations;
    }

    @Override
    public String getMessage() {
        if (!violations.isEmpty()) {
             return toMessageString((Set<ConstraintViolation<?>>) violations);
        }
        return super.getMessage();
    }

}

