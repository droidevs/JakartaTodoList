/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlets;

import Data.Category;
import Data.Todo;
import Exceptions.ArgumentRequiredException;
import Exceptions.InvalidDueDateException;
import Exceptions.ResourceNotFoundException;
import Exceptions.ValidationException;
import Models.CreateTodoRequest;
import Models.GetTodoRequest;
import Models.RequestFactory;
import Models.UpdateTodoRequest;
import Paths.BasePaths;
import Paths.PathParams;
import Paths.Paths;
import Paths.Router;
import Services.CategoryService;
import Services.impl.CategoryServiceImpl;
import Services.TodoService;
import Services.impl.TodoServiceImpl2;
import Utils.ExceptionHandlerUtil;
import Utils.SessionUtils;
import View.ViewDispatcher;
import View.ViewResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(BasePaths.TODOS + "/*")
public class TodoServlet extends HttpServlet {

    private TodoService todoService;
    private CategoryService categoryService;
    private Router router;

    @Override
    public void init() {
        this.todoService = new TodoServiceImpl2();
        this.categoryService = new CategoryServiceImpl();
        this.router = new Router();
    }

    // =====================
    // GET
    // =====================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Router.RouteMatch match = router.matchRoute(req);

            switch (match.getRoute()) {

                case TODOS_LIST:
                    listTodos(req, resp);
                    break;

                case TODOS_GET_ONE:
                    viewTodo(req, resp, match);
                    break;

                case TODOS_CREATE_FORM:
                    showCreateForm(req, resp);
                    break;

                case TODOS_EDIT_FORM:
                    showEditForm(req, resp, match);
                    break;

                default:
                    throw new ResourceNotFoundException();
            }

        } catch (Exception e) {
            ExceptionHandlerUtil.handle(req, resp, e, ViewResolver.TODOS);
        }
    }

    // =====================
    // POST
    // =====================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Router.RouteMatch match = router.matchRoute(req);

            switch (match.getRoute()) {

                case TODOS_CREATE:
                    createTodo(req, resp);
                    break;

                case TODOS_UPDATE:
                    updateTodo(req, resp, match);
                    break;

                case TODOS_DELETE:
                    deleteTodo(req, resp, match);
                    break;

                default:
                    throw new ResourceNotFoundException();
            }

        } catch (Exception e) {
            ExceptionHandlerUtil.handle(req, resp, e, ViewResolver.TODO_FORM);
        }
    }

    // =====================
    // ACTION METHODS
    // =====================
    private void listTodos(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        todoService.markOverdueTodos(userId);
        List<Todo> todos = todoService.getTodos(userId);

        req.setAttribute("todos", todos);
        ViewDispatcher.dispatch(req, resp, ViewResolver.TODOS);
    }

    private void viewTodo(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws IOException, ServletException {

        String idStr = match.getParams().get(PathParams.Todos.ID);
        Integer id = Integer.valueOf(idStr);

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        Todo todo = todoService.getTodo(new GetTodoRequest(id), userId);
        req.setAttribute("todo", todo);

        ViewDispatcher.dispatch(req, resp, ViewResolver.TODO_VIEW);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var userId = SessionUtils.getLoggedUserId(req);
        
        List<Category> categories = categoryService.getAll(userId);
        
        req.setAttribute("mode", "create");
        req.setAttribute("categories", categories);
        
        ViewDispatcher.dispatch(req, resp, ViewResolver.TODO_FORM);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws ServletException, IOException {

        String idStr = match.getParams().get(PathParams.Todos.ID);
        Integer id = Integer.valueOf(idStr);

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        Todo todo = todoService.getTodo(new GetTodoRequest(id), userId);

        req.setAttribute("todo", todo);
        req.setAttribute("mode", "edit");

        ViewDispatcher.dispatch(req, resp, ViewResolver.TODO_FORM);
    }

    private void createTodo(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, InvalidDueDateException, ArgumentRequiredException, ValidationException {

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        CreateTodoRequest createReq = RequestFactory.createTodo(req);

        todoService.createTodo(createReq, userId);
        resp.sendRedirect(req.getContextPath() + "/todos");
    }

    private void updateTodo(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws IOException {

        String idStr = match.getParams().get(PathParams.Todos.ID);
        Integer id = Integer.valueOf(idStr);

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        UpdateTodoRequest request = RequestFactory.updateTodo(req, id);

        todoService.updateTodo(request, userId);
        resp.sendRedirect(req.getContextPath() + Paths.Todos.LIST());
    }

    private void deleteTodo(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws IOException {

        String idStr = match.getParams().get(PathParams.Todos.ID);
        Integer id = Integer.valueOf(idStr);

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        todoService.deleteTodo(RequestFactory.deleteTodo(id), userId);
        resp.sendRedirect(req.getContextPath() + Paths.Todos.LIST());
    }
}
