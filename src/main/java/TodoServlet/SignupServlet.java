/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import Data.Database;
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

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("signup.jsp");
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException , IOException {
        
        String username = req.getParameter("username");
        String fullname = req.getParameter("fullname");
        String password = req.getParameter("password");
        
        // TODO: hash the password (recommanded : BCrypt)
        try(Connection conn = Database.getConnection()) {
            String sql = "SELECT id FROM users username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(sql);
            
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                req.setAttribute("error", "Username already exists");
                req.getRequestDispatcher("signup.jsp").forward(req, resp);
                return;
            }
            
            String insertSql = "INSERT INTO users (username, full_name, password) VALUES (?, ?, ?)";
            
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            
            insertStmt.setString(0, username);
            insertStmt.setString(1, fullname);
            insertStmt.setString(2, password); // TODO: store hashed password
            insertStmt.executeUpdate();
            
            
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            resp.sendRedirect(req.getContextPath() + "/todos");
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
