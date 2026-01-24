/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlets;

import Data.Category;
import Exceptions.ResourceNotFoundException;
import Models.GetCategoryRequest;
import Models.RequestFactory;
import Paths.BasePaths;
import Paths.PathParams;
import Paths.Paths;
import Paths.Router;
import Services.CategoryService;
import Services.impl.CategoryServiceImpl;
import Services.TodoService;
import Services.impl.TodoServiceImpl2;
import Utils.ExceptionHandlerUtil;
import Utils.ServletUtils;
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
import java.util.Map;
import java.util.HashMap;
import Data.Todo;

/**
 *
 * @author admin
 */
@WebServlet(BasePaths.CATEGORIES + "/*")
public class CategoryServlet extends HttpServlet {

    private final CategoryService categoryService;
    private final Router router;
    private final TodoService todoService;

    public CategoryServlet() {
        this.categoryService = new CategoryServiceImpl();
        this.router = new Router();
        this.todoService = new TodoServiceImpl2();
    }

    /* ==========================
       GET
       ========================== */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            System.out.println("CategoryServlet: handling GET " + req.getRequestURI());
            Router.RouteMatch match = router.matchRoute(req);

            System.out.println("CategoryServlet: matched route=" + match.getRoute());

            switch (match.getRoute()) {

                case CATEGORIES_LIST:
                    System.out.println("CategoryServlet: showing categories list");
                    showCategories(req, resp);
                    break;

                case CATEGORIES_GET_ONE:
                    System.out.println("CategoryServlet: showing single category");
                    showCategory(req, resp, match);
                    break;

                case CATEGORIES_CREATE_FORM:
                    System.out.println("CategoryServlet: showing create form");
                    showCreateForm(req, resp);
                    break;

                case CATEGORIES_EDIT_FORM :
                    System.out.println("CategoryServlet: showing edit form");
                    showEditForm(req, resp, match);
                    break;

                default :
                        throw new ResourceNotFoundException();
            }

        } catch (Exception e) {
            ExceptionHandlerUtil.handle(req, resp, e, ViewResolver.CATEGORIES);
        }
        }

        /* ==========================
       POST
       ========================== */
        @Override
        protected void doPost
        (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

            try {
                System.out.println("CategoryServlet: handling POST " + req.getRequestURI());
                Router.RouteMatch match = router.matchRoute(req);

                System.out.println("CategoryServlet: matched route=" + match.getRoute());

                switch (match.getRoute()) {

                    case CATEGORIES_CREATE:
                        System.out.println("CategoryServlet: create category");
                        createCategory(req, resp);
                        break;

                    case CATEGORIES_UPDATE:
                        System.out.println("CategoryServlet: update category");
                        updateCategory(req, resp, match);
                        break;

                    case CATEGORIES_DELETE:
                        System.out.println("CategoryServlet: delete category");
                        deleteCategory(req, resp, match);
                        break;

                    default:
                        throw new ResourceNotFoundException();
                }

            } catch (Exception e) {
                ExceptionHandlerUtil.handle(req, resp, e, ViewResolver.CATEGORY_FORM);
            }
        }

    
    
    

    private void showCategories(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        List<Category> categories = categoryService.getAll(userId);

        // compute todo counts per category for the current user
        Map<Integer, Integer> todoCounts = new HashMap<>();
        List<Todo> todos = todoService.getTodos(userId);
        if (todos != null) {
            for (Todo t : todos) {
                Integer cid = t.getCategory() != null ? t.getCategory().getId() : null;
                if (cid != null) {
                    todoCounts.put(cid, todoCounts.getOrDefault(cid, 0) + 1);
                }
            }
        }

        req.setAttribute("categories", categories);
        req.setAttribute("todoCounts", todoCounts);
        ViewDispatcher.dispatch(req, resp, ViewResolver.CATEGORIES);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("category", new Category());
        ViewDispatcher.dispatch(req, resp, ViewResolver.CATEGORY_FORM);
    }

    private void showCategory(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws Exception {

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        Integer id = Integer.valueOf(match.getParams().get(PathParams.Categories.ID));

        Category category = categoryService.get(
                RequestFactory.getCategory(id),
                userId
        );

        // get todos for this user and filter by category
        List<Todo> allTodos = todoService.getTodos(userId);
        List<Todo> todosForCategory = new java.util.ArrayList<>();
        if (allTodos != null) {
            for (Todo t : allTodos) {
                if (t.getCategory() != null && t.getCategory().getId() != null && t.getCategory().getId().equals(id)) {
                    todosForCategory.add(t);
                }
            }
        }

        req.setAttribute("category", category);
        req.setAttribute("todos", todosForCategory);
        ViewDispatcher.dispatch(req, resp, ViewResolver.CATEGORY_VIEW);
    }

    private void createCategory(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        categoryService.create(
                RequestFactory.createCategory(req),
                userId
        );

        resp.sendRedirect(req.getContextPath() + Paths.Categories.LIST());
    }

    private void updateCategory(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws Exception {

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        Integer id = Integer.valueOf(match.getParams().get(PathParams.Categories.ID));

        categoryService.update(
                RequestFactory.updateCategory(req, id),
                userId
        );

        resp.sendRedirect(req.getContextPath() + Paths.Categories.LIST());
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp,
            Router.RouteMatch match)
            throws Exception {

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        Integer id = Integer.valueOf(match.getParams().get(PathParams.Categories.ID));

        categoryService.delete(
                RequestFactory.deleteCategory(id),
                userId
        );

        resp.sendRedirect(req.getContextPath() + Paths.Categories.LIST());
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp, Router.RouteMatch match) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        Integer id = Integer.valueOf(match.getParams().get(PathParams.Categories.ID));

        Category category = categoryService.get(
                RequestFactory.getCategory(id),
                userId
        );

        req.setAttribute("category", category);
        ViewDispatcher.dispatch(req, resp, ViewResolver.CATEGORY_FORM);
    }
}
