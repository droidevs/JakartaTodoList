/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package TodoServlet;

import Data.User;
import Models.RegisterRequest;
import Services.AuthService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Mouad OUMOUS
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private final AuthService authService;

    public SignupServlet() {
        this.authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("signup.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String fullname = req.getParameter("fullname");
        String password = req.getParameter("password");

        try {
            User user = authService.register(new RegisterRequest(username, fullname, password, password)); // todo: implement in jsp
            
            HttpSession session = req.getSession();
            session.setAttribute("uderId", user.getId());
            session.setAttribute("user", user.getUsername());
            resp.sendRedirect(req.getContextPath() + "/todos");

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
            
        }
    }
}
