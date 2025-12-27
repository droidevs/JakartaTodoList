/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import Models.GetTodoRequest;
import Services.TodoService;
import Services.impl.TodoServiceImpl2;
import Utils.ExceptionHandlerUtil;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author Mouad OUMOUS
 */
@WebServlet("/todo/view")
public class ViewTodoServlet extends HttpServlet {

    private final TodoService todoService;

    public ViewTodoServlet() {
        //this.todoService = new TodoServiceImpl();
        this.todoService = new TodoServiceImpl2();
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
            var sessionUser = (Integer) request.getSession().getAttribute("userId");
            
            var todo = todoService.getTodo(new GetTodoRequest(id), 
                    sessionUser
            );
            request.setAttribute("todo", todo);
            request.getRequestDispatcher("/ViewTodo.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath()+ "/todos");
        } catch(Exception e) {
            ExceptionHandlerUtil.handle(request, response, e, "TodoView.jsp");
        }
    }
    
}
