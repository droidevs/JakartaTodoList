/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Errors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author admin
 */

public class ErrorUtils {

   
    public static String errorAttribute = "error";
    /**
     * Send HTTP error based on BusinessError object.
     * @param response
     * @param error
     */
    public static void sendError(HttpServletResponse response, BusinessError error) {
        try {
            response.sendError(error.getHttpStatus(), error.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace(); // fallback logging
        }
    }

    /**
     * Forward request to a JSP page with error attributes.
     * @param request
     * @param response
     * @param page
     * @param message
     */
    public static void forwardWithError(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        String page,
                                        String message) {
        try {
            request.setAttribute(errorAttribute, message);
            request.getRequestDispatcher(page).forward(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
        }
    }
}

