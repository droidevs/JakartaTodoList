/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package Filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Mouad OUMOUS
 */
@WebFilter(filterName = "HomeFilter", urlPatterns = {"/"})
public class HomeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) sr;
        HttpServletResponse resp = (HttpServletResponse) sr1;
        
        HttpSession session = req.getSession(false);
        
        String path = req.getRequestURI().substring(req.getContextPath().length());
       
        System.out.println(path);
        
        if (path.equals("/") || path.isEmpty()) {
            System.out.println(true);
            // The request is for the root of your web app
            if (session != null && session.getAttribute("user") != null) {
                // User is signed in redirect to todos
                resp.sendRedirect(req.getContextPath() + "/todos");
                return;
            }
        }
        
        chain.doFilter(sr, sr1);
    }
    
}
