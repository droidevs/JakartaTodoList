/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author admin
 */
public class ViewDispatcher {

    public static void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        if (page == null) {
            System.out.println("ViewDispatcher: page is null, cannot dispatch");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No view specified");
            return;
        }

        // page can be either a full path (from ViewResolver constants) or a view name
        String original = page;
        String path;
        if (page.startsWith("/WEB-INF/")) {
            path = page;
        } else {
            path = ViewResolver.resolve(page);
        }

        System.out.println("ViewDispatcher: dispatching page=\"" + original + "\" resolvedPath=\"" + path + "\"");

        // Diagnostic: check that the resource exists in the webapp
        try {
            URL resourceUrl = request.getServletContext().getResource(path);
            if (resourceUrl == null) {
                System.out.println("ViewDispatcher: resource not found at path=" + path);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "View not found: " + path);
                return;
            } else {
                System.out.println("ViewDispatcher: resource exists: " + resourceUrl.toString());
            }
        } catch (MalformedURLException mue) {
            System.out.println("ViewDispatcher: invalid path URL for path=" + path + " error=" + mue.getMessage());
            // continue and attempt to dispatch
        }

        RequestDispatcher rd = request.getRequestDispatcher(path);
        if (rd == null) {
            System.out.println("ViewDispatcher: RequestDispatcher is null for path=" + path);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "View not found: " + path);
            return;
        }

        try {
            rd.forward(request, response);
        } catch (Throwable t) {
            // Catch broad Throwable to log JSP compilation/runtime errors too
            System.out.println("ViewDispatcher: exception while forwarding to " + path + ": " + t.getClass().getName() + " - " + t.getMessage());
            t.printStackTrace(System.out);
            // Propagate as ServletException for container handling
            if (t instanceof ServletException) {
                throw (ServletException) t;
            } else if (t instanceof IOException) {
                throw (IOException) t;
            } else {
                throw new ServletException(t);
            }
        }
    }

}
