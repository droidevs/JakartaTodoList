/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Exceptions.ResourceAccessDeniedException;
import Exceptions.ResourceNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class ServletUtils {
    
    public static Integer requireUser(HttpServletRequest req)
            throws ServletException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            throw new ResourceAccessDeniedException();
        }
        return (Integer) session.getAttribute("userId");
    }
    
    public static String[] getPathInfoParts(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        
        if(pathInfo == null)
            throw new ResourceNotFoundException();
        
        return pathInfo.split("/");
    }
}
