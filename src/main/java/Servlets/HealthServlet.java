package Servlets;

import Utils.HiberneteUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Session;

/**
 * Simple health check servlet. Returns 200 if application is up and able to connect to DB.
 */
@WebServlet(name = "HealthServlet", urlPatterns = {"/health"})
public class HealthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        final boolean[] dbOk = {false};
        String message = "OK";

        Session session = null;
        try {
            session = HiberneteUtil.getSessionFactory().openSession();
            session.doWork((Connection conn) -> {
                try {
                    if (conn != null && !conn.isClosed()) {
                        dbOk[0] = true;
                    }
                } catch (SQLException ex) {
                    // propagate to outer catch
                    throw new RuntimeException(ex);
                }
            });
        } catch (Exception e) {
            message = e.getMessage();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        try (PrintWriter out = resp.getWriter()) {
            if (dbOk[0]) {
                resp.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"status\":\"ok\",\"database\":true}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"status\":\"error\",\"database\":false,\"message\":\"" + escapeJson(message) + "\"}");
            }
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
