/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author admin
 */
@WebServlet("/todo/create")
public class TodoFormServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Integer id;
        
        try {
            id = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0;
        }
        
        Todo todo = new Todo();
        if (id > 0) {
            var o = TodoStore.getTodo(id);
            if (o != null) {
                todo = o;
            }
        }
        
        request.setAttribute("todo", todo);
        
        // Show the form page
        request.getRequestDispatcher("/TodoForm.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> errors = new HashMap();
        
       String title = request.getParameter("title");
       String description = request.getParameter("description");
       
        if (title == null || title.trim().isBlank()) errors.put("title", "Title is required");
        if (description == null || description.trim().isBlank()) errors.put("description", "description is required");
        
        Integer id;
        try {
            id = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0;
        }
        
        Todo todo = new Todo();
        if (id != 0) {
            todo.setId(id);
        }
        todo.setTitle(title);
        todo.setDescription(description);
        
        if (errors.isEmpty()) {
            if (id != 0) {
                TodoStore.updateTodo(todo);
            } else {
                TodoStore.addTodo(todo);
            }
            response.sendRedirect(request.getContextPath() + "/todos");
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("todo", todo);
            getServletContext().getRequestDispatcher("/TodoForm.jsp").forward(request, response);
        }
        
    }

}
