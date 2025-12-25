/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import Repositories.UserRepository;
import Repositories.impl.UserRepositoryJdbc;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Mouad OUMOUS
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        HttpSession session = req.getSession(false); // get session if exists
        if (session != null) {
            session.invalidate(); // remove all session attributes
        }
        resp.sendRedirect("login.jsp");
    }

}
