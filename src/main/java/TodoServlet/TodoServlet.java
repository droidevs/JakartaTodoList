/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;
import Repositories.TodoRepository;
import Repositories.impl.TodoRepositoryJdbc;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/todos")
public class TodoServlet extends HttpServlet {

    private final TodoRepository todoRepository;

    public TodoServlet() {
        this.todoRepository = new TodoRepositoryJdbc();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = (HttpSession) req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        // (auth filter) the session is already garanteed and all its attributes are there
        
        List<Todo> todos = todoRepository.findByUserId(userId);
        req.setAttribute("todos", todos);
        req.getRequestDispatcher("/todos.jsp").forward(req, resp);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/todos");
    }
 
}