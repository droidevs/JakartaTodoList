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
import java.net.Authenticator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mouad OUMOUS
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private final UserRepository userRepository;

    public SignupServlet() {
        this.userRepository = new UserRepositoryJdbc();
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("signup.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException , IOException {
        
        String username = req.getParameter("username");
        String fullname = req.getParameter("fullname");
        String password = req.getParameter("password");
        
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        // TODO: hash the password (recommanded : BCrypt)
        try(Connection conn = Database.getConnection()) {
            User user = userRepository.findByUsername(username);
            
            if (user != null) {
                req.setAttribute("error", "Username already exists");
                req.getRequestDispatcher("signup.jsp").forward(req, resp);
                return;
            }
            
            String insertSql = "INSERT INTO users (username, full_name, password) VALUES (?, ?, ?)";
            
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setFullName(fullname);
            newUser.setPasswordHash(hashedPassword);
            
            userRepository.save(newUser);
            
            HttpSession session = req.getSession();
            session.setAttribute("uderId", newUser.getId());
            session.setAttribute("user", username);
            resp.sendRedirect(req.getContextPath() + "/todos");
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
