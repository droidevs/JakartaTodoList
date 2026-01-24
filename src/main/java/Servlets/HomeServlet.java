/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import View.ViewDispatcher;
import View.ViewResolver;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Mouad OUMOUS
 */
@WebServlet("/")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        System.out.println("HomeServlet: GET " + uri);

        // If request is for favicon or other static resource, let the container serve it
        String path = uri.substring(req.getContextPath().length());
        if (path.equals("/favicon.ico") || path.startsWith("/static/") || path.startsWith("/resources/")) {
            System.out.println("HomeServlet: detected static resource request, forwarding to resource: " + path);
            RequestDispatcher rd = req.getRequestDispatcher(path);
            if (rd != null) {
                rd.forward(req, resp);
                return;
            }
        }

        ViewDispatcher.dispatch(req, resp, ViewResolver.INDEX);
    }

}
