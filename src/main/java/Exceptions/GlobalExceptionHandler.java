/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *
 * @author admin
 */
@WebServlet(name = "GlobalExceptionHandler", urlPatterns = "/error-handler")
public class GlobalExceptionHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleError(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleError(request, response);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        String servletName = (String) request.getAttribute("jakarta.servlet.error.servlet_name");
        String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");

        if (servletName == null) servletName = "Unknown";

        request.setAttribute("statusCode", statusCode);
        request.setAttribute("message", message);
        request.setAttribute("exception", throwable);
        request.setAttribute("servletName", servletName);
        request.setAttribute("requestUri", requestUri);

        // Forward to a friendly JSP
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}

