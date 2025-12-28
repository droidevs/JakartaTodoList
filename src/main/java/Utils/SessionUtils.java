/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Data.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public final class SessionUtils {
    
    
    private SessionUtils() { 
    }
    
    
    public static void createUserSession(HttpServletRequest request, User user) {
        
        HttpSession session = request.getSession(true);
        
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("fullName", user.getFullName());
        
        // optional : session timeout (seconds)
        session.setMaxInactiveInterval(30 * 60);
    }
    
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if(session != null) {
            session.invalidate();
        }
    }
    
    public static Integer getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        return session != null ? (Integer) session.getAttribute("userId") : null;
    }
    
    public static boolean isAuthenticated(HttpServletRequest request) {
        return getLoggedUserId(request) != null;
    }
}
