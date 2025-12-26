/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author admin
 */
public class ViewDispatcher {
    
    
    public static void dispatch(HttpServletRequest request,HttpServletResponse response, String page) throws ServletException, IOException {
        String path = ViewResolver.resolve(page);
        request.getRequestDispatcher(path).forward(request, response);
    }
    
}
