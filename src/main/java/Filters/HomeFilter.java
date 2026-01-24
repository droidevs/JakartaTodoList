package Filters;

import Paths.Api;
import Utils.SessionUtils;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        boolean isLoggedIn = SessionUtils.isAuthenticated(req);

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.equals("/") || path.isEmpty()) {
            // The request is for the root of your web app
            if (isLoggedIn) {
                System.out.println("HomeFilter: User is signed in, redirecting to todos");
                // User is signed in redirect to todos
                resp.sendRedirect(req.getContextPath() + Api.TODOS_LIST.getPath());
                return;
            }
        }

        chain.doFilter(sr, sr1);
    }

}
