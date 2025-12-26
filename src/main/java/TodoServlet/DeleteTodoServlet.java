/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import Exceptions.ActionDeniedException;
import Models.DeleteTodoRequest;
import Repositories.TodoRepository;
import Repositories.impl.TodoRepositoryJdbc;
import Services.TodoService;
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


    private final TodoService todoService;

    public DeleteTodoServlet() {
        this.todoService = new TodoService();
    }
    
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
            Integer sessionUser = (Integer) request.getSession().getAttribute("userId");
            var deleteRequest = new DeleteTodoRequest(id);
            todoService.deleteTodo(deleteRequest, sessionUser);
        } catch (NumberFormatException e) {
            id = -1;
        } catch (ActionDeniedException e) {
            request.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/todos");
        }
        
        response.sendRedirect(request.getContextPath()+"/todos");
    }
   
}
