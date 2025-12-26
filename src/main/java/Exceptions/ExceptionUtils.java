/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;


/**
 *
 * @author admin
 */
public class ExceptionUtils {

    /**
     * Check whether the exception should be forwarded to the page (true) 
     * or sent as an HTTP error (false)
     * @param e
     * @return 
     */
    public static boolean shouldForward(Exception e) {
        // Forward only if it is a validation or business-related form error
        return e instanceof TodoValidationException
                || e instanceof ArgumentRequiredException
                || e instanceof InvalidDueDateException
                || e instanceof PasswordNotConfirmedException
                || e instanceof IncorrectPasswordException
                || e instanceof UserAlreadyExistsException;
    }
}
