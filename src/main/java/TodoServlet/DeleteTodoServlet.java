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
import java.util.Objects;

/**
 *
 * @author Mouad OUMOUS
 */
@WebServlet("/todo/delete")
public class DeleteTodoServlet extends HttpServlet {


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
            var todoUser = TodoStore.getUserIdForTodo(id);
            var sessionUser = (Integer) request.getSession().getAttribute("userId");
            if(!Objects.equals(todoUser, sessionUser)){
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "You are not allowed to delete this resource");
                return;
            }
        } catch (NumberFormatException e) {
            id = 0;
        }
       
        if (id > 0) {
            TodoStore.deleteTodo(id);
        }
        
        response.sendRedirect(request.getContextPath()+"/todos");
    }
   
}
