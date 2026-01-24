/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Errors.BusinessError;
import Errors.BusinessErrorMapper;
import Errors.ErrorUtils;
import Exceptions.ExceptionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;


public class ExceptionHandlerUtil {

    /**
     * Handle any exception in a servlet
     * Decides whether to forward or send HTTP error
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param e        Exception to handle
     * @param forwardPage JSP page to forward if forwarding is allowed (can be null)
     * @throws jakarta.servlet.ServletException
     */
    public static void handle(HttpServletRequest request,
                              HttpServletResponse response,
                              Exception e,
                              String forwardPage) throws ServletException, IOException {

        BusinessError error = BusinessErrorMapper.map(e);

        if (ExceptionUtils.shouldForward(e) && forwardPage != null) {
            // Forward to page with business error
            ErrorUtils.forwardWithError(request, response, forwardPage, error.getMessage());
        } else {
            // Send HTTP error
            ErrorUtils.sendError(response, error);
        }
    }
}