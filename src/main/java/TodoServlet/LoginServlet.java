/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import Data.Database;
import Data.User;
import Repositories.UserRepository;
import Repositories.impl.UserRepositoryJdbc;
import Utils.PasswordUtil;
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

    private final UserRepository userRepository;

    public LoginServlet() {
        userRepository = new UserRepositoryJdbc();
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
            User user = userRepository.findByUsername(username);

            if (user != null) {
                String hashedPassword = user.getPasswordHash();
                if (PasswordUtil.verifyPassword(password, hashedPassword)) {
                    // Password correct
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", ""+user.getId());
                    session.setAttribute("user", username);
                    response.sendRedirect(request.getContextPath() + "/todos");
                    
                } else {
                    request.setAttribute("error", "Invalid username or password");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                // invalid credentals
                request.setAttribute("error", "User does not exist with this username please signup");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
