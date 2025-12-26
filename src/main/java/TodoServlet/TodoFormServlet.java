/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import Constants.TodoStatus;
import Data.Todo;
import Errors.BusinessErrorMapper;
import Exceptions.ArgumentRequiredException;
import Exceptions.InvalidDueDateException;
import Exceptions.ResourceNotFoundException;
import Exceptions.TodoValidationException;
import Models.CreateTodoRequest;
import Models.GetTodoRequest;
import Models.UpdateTodoRequest;
import Repositories.TodoRepository;
import Repositories.impl.TodoRepositoryJdbc;
import Services.TodoService;
import Utils.ExceptionHandlerUtil;
import View.ViewDispatcher;
import View.ViewResolver;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Mouad OUMOUS
 */
@WebServlet("/todo/create")
public class TodoFormServlet extends HttpServlet {

    private final TodoService todoService;

    public TodoFormServlet() {
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
            // if the id is there then we are in the edit mode 
            // we check if the edited resource is for the logged in user
            var sessionUser = (Integer) request.getSession().getAttribute("userId");
            var todo = todoService.getTodo(new GetTodoRequest(id), sessionUser);

            request.setAttribute("todo", todo);
            // Show the form page
            request.getRequestDispatcher("/TodoForm.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("todo", new Todo());
            // Show the form page
            ViewDispatcher.dispatch(request, response, ViewResolver.TODO_FORM);
        } catch (Exception e) {
            ExceptionHandlerUtil.handle(request, response, e, "TodoForm.jsp");
        }

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
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("dueDate");
        String status = request.getParameter("status");

        LocalDate dueDate = null;

        if (dueDateStr != null && !dueDateStr.isBlank()) {
            dueDate = LocalDate.parse(dueDateStr);
        }
        
        var sessionUser = (Integer) request.getSession().getAttribute("userId");
        Integer id = null;

        try {
            id = Integer.valueOf(request.getParameter("id"));
            var updateRequest = new UpdateTodoRequest(id, title, description, dueDate, TodoStatus.valueOf(status));
            todoService.updateTodo(updateRequest, sessionUser);
        } catch (NumberFormatException e) {
            id = -1;
        } catch (Exception e) {
            ExceptionHandlerUtil.handle(request, response, e, "TodoForm.jsp");
            return;
        }

        if (id == -1) {
            var createRequest = new CreateTodoRequest(title, description, TodoStatus.NEW, dueDate);
            try {
                todoService.createTodo(createRequest, sessionUser);
            }catch (Exception e) {
                ExceptionHandlerUtil.handle(request, response, e, ViewResolver.TODO_FORM);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/todos");

        }

    }
}
