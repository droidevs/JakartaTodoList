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

/**
 *
 * @author admin
 */
@WebServlet(BasePaths.CATEGORIES + "/*")
public class CategoryServlet extends HttpServlet {

    private final CategoryService categoryService;
    private final Router router;

    public CategoryServlet() {
        this.categoryService = new CategoryServiceImpl();
        this.router = new Router();
    }

    /* ==========================
       GET
       ========================== */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Router.RouteMatch match = router.matchRoute(req);

            switch (match.getRoute()) {

                case CATEGORIES_LIST:
                    showCategories(req, resp);
                    break;

                case CATEGORIES_GET_ONE:
                    showCategory(req, resp, match);
                    break;

                case CATEGORIES_CREATE_FORM:
                    showCreateForm(req, resp);
                    break;

                case CATEGORIES_EDIT_FORM :
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
                Router.RouteMatch match = router.matchRoute(req);

                switch (match.getRoute()) {

                    case CATEGORIES_CREATE:
                        createCategory(req, resp);
                        break;

                    case CATEGORIES_UPDATE:
                        updateCategory(req, resp, match);
                        break;

                    case CATEGORIES_DELETE:
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

        req.setAttribute("categories", categories);
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

        req.setAttribute("category", category);
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
