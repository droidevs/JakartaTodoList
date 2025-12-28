/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets.oldservlets;
import Data.Todo;
import Services.TodoService;
import Services.impl.TodoServiceImpl2;
import View.ViewDispatcher;
import View.ViewResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/todos")
public class TodosServlet extends HttpServlet {

    //private final TodoServiceImpl todoService;
    private final TodoService todoService;

    public TodosServlet() {
        //this.todoService = new TodoServiceImpl();
        this.todoService = new TodoServiceImpl2();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = (HttpSession) req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        // (auth filter) the session is already garanteed and all its attributes are there
        todoService.markOverdueTodos(userId);
        
        List<Todo> todos = todoService.getTodos(userId);
        req.setAttribute("todos", todos);
        ViewDispatcher.dispatch(req, resp, ViewResolver.TODOS);

    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/todos");
    }
 
}