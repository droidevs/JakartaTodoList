/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Filters;


import Paths.Api;
import Utils.SessionUtils;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Skip non-REQUEST dispatcher types (e.g., FORWARD, INCLUDE) so internal JSP includes do not trigger redirects
        DispatcherType dt = req.getDispatcherType();
        System.out.println("AuthFilter: dispatcherType=" + dt);
        if (dt != DispatcherType.REQUEST) {
            chain.doFilter(request, response);
            return;
        }

        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("AuthFilter: evaluating path='" + path + "'");

        // Skip filtering for internal JSP includes or WEB-INF resources and static assets
        boolean isWebInf = path.startsWith("/WEB-INF/");
        boolean isStatic = path.startsWith("/resources/") || path.startsWith("/static/") || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".ico");
        if (isWebInf || isStatic) {
            System.out.println("AuthFilter: skipping (webinf/static) path='" + path + "'");
            chain.doFilter(request, response);
            return;
        }

        if ("/".equals(path)) {
            // Skip filtering for root path
            System.out.println("AuthFilter: root path, skipping");
            chain.doFilter(request, response);
            return;
        }
        // Allow Login page, register page and static resources
        boolean allowed = path.contains("login") || path.contains("register") || path.contains("css") || path.contains("js");
        if (allowed) {
            System.out.println("AuthFilter: allowed path='" + path + "'");
            chain.doFilter(request, response);
            return;
        }

        boolean isLogged = SessionUtils.getLoggedUserId(req) != null;
        if (!isLogged) {
            System.out.println("AuthFilter: User not logged in (path='" + path + "'), redirecting to login page");
            resp.sendRedirect(req.getContextPath() + Api.AUTH_LOGIN.getPath());
            return; // stop filter chain after redirect
        } else {
            System.out.println("AuthFilter: User authenticated, continue chain");
            chain.doFilter(request, response);
        }

    }

}
