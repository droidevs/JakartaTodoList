/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Errors;

import Exceptions.ActionDeniedException;
import Exceptions.ArgumentRequiredException;
import Exceptions.IncorrectPasswordException;
import Exceptions.InvalidDueDateException;
import Exceptions.PasswordNotConfirmedException;
import Exceptions.ResourceAccessDeniedException;
import Exceptions.ResourceNotFoundException;
import Exceptions.TodoValidationException;
import Exceptions.UserAlreadyExistsException;
import Exceptions.UserNotFoundException;
import Exceptions.ValidationException;
import Validators.ValidationUtils;

/**
 *
 * @author admin
 */

public class BusinessErrorMapper {

    
    /**
     * Map a custom exception to a BusinessError object with HTTP status and message.
     * @param e
     * @return 
     */
    public static BusinessError map(Exception e) {

        // Validation errors
        if (e instanceof /*TodoValidationException*/ ValidationException || e instanceof ArgumentRequiredException || e instanceof InvalidDueDateException) {
            return new BusinessError(400, e.getMessage());
        }

        // Authentication / password errors
        if (e instanceof IncorrectPasswordException || e instanceof PasswordNotConfirmedException) {
            return new BusinessError(401, e.getMessage());
        }

        // User management errors
        if (e instanceof UserAlreadyExistsException) {
            return new BusinessError(409, e.getMessage());
        }
        if (e instanceof UserNotFoundException) {
            return new BusinessError(404, e.getMessage());
        }

        // Resource access
        if (e instanceof ResourceAccessDeniedException || e instanceof ActionDeniedException) {
            return new BusinessError(403, e.getMessage());
        }

        // Resource not found
        if (e instanceof ResourceNotFoundException) {
            return new BusinessError(404, e.getMessage());
        }

        // Default: internal server error
        return new BusinessError(500, e.getMessage());
    }
}

