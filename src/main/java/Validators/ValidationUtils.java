/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validators;

import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author admin
 */
public class ValidationUtils {

    public static <T> String toMessageString(Set<ConstraintViolation<T>> violations) {
        if (violations == null || violations.isEmpty()) {
            return "";
        }

        return violations.stream()
                .map(v -> v.getPropertyPath() + " " + v.getMessage())
                .collect(Collectors.joining(", "));
    }

    public static <T> List<String> toMessageList(Set<ConstraintViolation<T>> violations) {
        if (violations == null) {
            return List.of();
        }

        return violations.stream()
                .map(v -> v.getPropertyPath() + " " + v.getMessage())
                .toList();
    }

}
