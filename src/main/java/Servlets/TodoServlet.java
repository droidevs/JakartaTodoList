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
            System.out.println("TodoServlet: handling GET " + req.getRequestURI());
            Router.RouteMatch match = router.matchRoute(req);

            System.out.println("TodoServlet: matched route=" + match.getRoute());

            switch (match.getRoute()) {

                case TODOS_LIST:
                    System.out.println("TodoServlet: listing todos");
                    listTodos(req, resp);
                    break;

                case TODOS_GET_ONE:
                    System.out.println("TodoServlet: viewing single todo");
                    viewTodo(req, resp, match);
                    break;

                case TODOS_CREATE_FORM:
                    System.out.println("TodoServlet: showing create form");
                    showCreateForm(req, resp);
                    break;

                case TODOS_EDIT_FORM:
                    System.out.println("TodoServlet: showing edit form");
                    showEditForm(req, resp, match);
                    break;

                default:
                    throw new ResourceNotFoundException();
            }

        } catch (Exception e) {
            ExceptionHandlerUtil.handle(req, resp, e, Paths.Todos.LIST());
        }
    }
    // POST
    // =====================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            System.out.println("TodoServlet: handling POST " + req.getRequestURI());
            Router.RouteMatch match = router.matchRoute(req);

            System.out.println("TodoServlet: matched route=" + match.getRoute());

            switch (match.getRoute()) {

                case TODOS_CREATE:
                    System.out.println("TodoServlet: create todo");
                    try {
                        createTodo(req, resp);
                    } catch (ValidationException | ArgumentRequiredException ex) {
                        System.out.println("TodoServlet: create validation failed: " + ex.getMessage());
                        // reload categories and show form with error
                        var userId = SessionUtils.getLoggedUserId(req);
                        List<Category> categories = categoryService.getAll(userId);
                        req.setAttribute("categories", categories);
                        req.setAttribute("error", ex.getMessage());
                        ViewDispatcher.dispatch(req, resp, ViewResolver.TODO_FORM);
                    }
                    break;

                case TODOS_UPDATE:
                    System.out.println("TodoServlet: update todo");
                    updateTodo(req, resp, match);
                    break;

                case TODOS_DELETE:
                    System.out.println("TodoServlet: delete todo");
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

        int userId = SessionUtils.getLoggedUserId(req);

        todoService.markOverdueTodos(userId);
        List<Todo> todos = todoService.getTodos(userId);

        req.setAttribute("todos", todos);
        ViewDispatcher.dispatch(req, resp, ViewResolver.TODOS);
    }

    private void viewTodo(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws IOException, ServletException {

        String idStr = match.getParams().get(PathParams.Todos.ID);
        int id = Integer.parseInt(idStr);

        int userId = SessionUtils.getLoggedUserId(req);

        Todo todo = todoService.getTodo(new GetTodoRequest(id), userId);
        req.setAttribute("todo", todo);

        ViewDispatcher.dispatch(req, resp, ViewResolver.TODO_VIEW);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer userId = SessionUtils.getLoggedUserId(req);

        // Do not force categories in create form: if categoryId query param is provided we keep it hidden,
        // otherwise load categories so the user can choose a category during creation.
        req.setAttribute("mode", "create");
        String categoryIdParam = req.getParameter("categoryId");
        if (categoryIdParam != null && !categoryIdParam.isBlank()) {
            req.setAttribute("categoryId", categoryIdParam);
        } else {
            // load user's categories so create form can offer a selector
            if (userId != null) {
                List<Category> categories = categoryService.getAll(userId);
                req.setAttribute("categories", categories);
            } else {
                req.setAttribute("categories", java.util.Collections.emptyList());
            }
        }

        ViewDispatcher.dispatch(req, resp, ViewResolver.TODO_FORM);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws ServletException, IOException {

        String idStr = match.getParams().get(PathParams.Todos.ID);
        int id = Integer.parseInt(idStr);

        int userId = SessionUtils.getLoggedUserId(req);

        Todo todo = todoService.getTodo(new GetTodoRequest(id), userId);

        req.setAttribute("todo", todo);
        // Load user's categories so the edit form can present the category select
        List<Category> categories = categoryService.getAll(userId);
        req.setAttribute("categories", categories);

        ViewDispatcher.dispatch(req, resp, ViewResolver.TODO_FORM);
    }

    private void createTodo(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, InvalidDueDateException, ArgumentRequiredException, ValidationException {

        Integer userId = SessionUtils.getLoggedUserId(req);

        if (userId == null) {
            System.out.println("TodoServlet.createTodo: no logged user, redirecting to login");
            resp.sendRedirect(req.getContextPath() + Paths.Auth.LOGIN());
            return;
        }

        CreateTodoRequest createReq = RequestFactory.createTodo(req);

        // Diagnostic logging
        System.out.println("TodoServlet.createTodo: userId=" + userId + ", createReq=" + createReq);

        // Category is optional now; createReq.getCategoryId() may be null
        try {
            Todo created = todoService.createTodo(createReq, userId);
            System.out.println("TodoServlet.createTodo: created todo id=" + (created != null ? created.getId() : "null"));
        } catch (Exception e) {
            System.out.println("TodoServlet.createTodo: exception during create: " + e);
            e.printStackTrace();
            throw e; // rethrow to be handled by outer catch
        }
        resp.sendRedirect(req.getContextPath() + Paths.Todos.LIST());
    }

    private void updateTodo(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws IOException {

        String idStr = match.getParams().get(PathParams.Todos.ID);
        int id = Integer.parseInt(idStr);

        int userId = SessionUtils.getLoggedUserId(req);

        UpdateTodoRequest request = RequestFactory.updateTodo(req, id);

        todoService.updateTodo(request, userId);
        resp.sendRedirect(req.getContextPath() + Paths.Todos.LIST());
    }

    private void deleteTodo(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws IOException {

        String idStr = match.getParams().get(PathParams.Todos.ID);
        int id = Integer.parseInt(idStr);

        int userId = SessionUtils.getLoggedUserId(req);

        todoService.deleteTodo(RequestFactory.deleteTodo(id), userId);
        resp.sendRedirect(req.getContextPath() + Paths.Todos.LIST());
    }
}
