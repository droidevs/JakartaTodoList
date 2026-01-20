/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlets;

import Data.User;
import Exceptions.IncorrectPasswordException;
import Exceptions.PasswordNotConfirmedException;
import Exceptions.ResourceNotFoundException;
import Exceptions.UserAlreadyExistsException;
import Exceptions.UserNotFoundException;
import Models.RequestFactory;
import Paths.BasePaths;
import Paths.HttpMethod;
import Paths.Paths;
import Paths.Route;
import Paths.Router;
import Services.AuthService;
import Services.impl.AuthServiceImpl;
import Utils.ExceptionHandlerUtil;
import Utils.ServletUtils;
import Utils.SessionUtils;
import View.ViewDispatcher;
import View.ViewResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author admin
 */
@WebServlet(BasePaths.AUTH +"/*")
public class AuthServlet extends HttpServlet {

    private AuthServiceImpl authService;
    private Router router;

    @Override
    public void init() {
        this.authService = new AuthServiceImpl();
        this.router = new Router();
    }

    // =======================
    // GET HANDLER
    // =======================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Router.RouteMatch match = router.matchRoute(req);

            switch (match.getRoute()) {

                case AUTH_LOGIN:
                    showLogin(req, resp);
                    break;

                case AUTH_REGISTER:
                    showRegister(req, resp);
                    break;

                case AUTH_LOGOUT:
                    logout(req, resp);
                    break;

                default:
                    throw new ResourceNotFoundException();
            }

        } catch (Exception e) {
            ExceptionHandlerUtil.handle(req, resp, e, ViewResolver.LOGIN);
        }
    }

    // =======================
    // POST HANDLER
    // =======================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Router.RouteMatch match = router.matchRoute(req);

            switch (match.getRoute()) {

                case AUTH_LOGIN_POST:
                    login(req, resp);
                    break;

                case AUTH_REGISTER_POST:
                    register(req, resp);
                    break;

                default:
                    throw new ResourceNotFoundException();
            }

        } catch (Exception e) {
            ExceptionHandlerUtil.handle(req, resp, e, ViewResolver.LOGIN);
        }
    }

    // =======================
    // ACTION METHODS (NO TRY/CATCH)
    // =======================

    private void showLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ViewDispatcher.dispatch(req, resp, ViewResolver.LOGIN);
    }

    private void showRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ViewDispatcher.dispatch(req, resp, ViewResolver.SIGNUP);
    }

    private void login(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, UserNotFoundException, IncorrectPasswordException {

        User user = authService.login(RequestFactory.login(req));

        SessionUtils.createUserSession(req, user);

        resp.sendRedirect(req.getContextPath() + Paths.Todos.LIST());
    }

    private void register(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, UserAlreadyExistsException, PasswordNotConfirmedException {

        User user = authService.register(
                RequestFactory.register(req)
        );

        SessionUtils.createUserSession(req, user);

        resp.sendRedirect(req.getContextPath() + Paths.Todos.LIST());
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        SessionUtils.invalidateSession(req);

        resp.sendRedirect(req.getContextPath() + Paths.Auth.LOGIN());
    }
}
