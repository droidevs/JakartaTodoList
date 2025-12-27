/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import Data.Database;
import Data.User;
import Models.LoginRequest;
import Repositories.UserRepository;
import Repositories.impl.UserRepositoryJdbc;
import Services.impl.AuthServiceImpl;
import Utils.ExceptionHandlerUtil;
import Utils.PasswordUtil;
import View.ViewResolver;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mouad OUMOUS
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AuthServiceImpl service;

    public LoginServlet() {
        service = new AuthServiceImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = service.login(new LoginRequest(username, password));

            HttpSession session = request.getSession();
            session.setAttribute("userId", "" + user.getId());
            session.setAttribute("user", username);
            response.sendRedirect(request.getContextPath() + "/todos");

        } catch (Exception e) {
            ExceptionHandlerUtil.handle(request, response, e, ViewResolver.LOGIN);
        }
    }
}
