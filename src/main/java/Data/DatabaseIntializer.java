/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author admin
 */

@WebListener
public class DatabaseIntializer implements ServletContextListener {
    
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try(Connection conn = Database.getConnection()) {
            Statement stmt = conn.createStatement();
            
            // Create table if not exists
            String sqlTodosTable = "CREATE TABLE IF NOT EXSTS todos (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(255) NOT NULL," +
                    "description TEXT" +
                    ");";
            String sqlUsersTable = "CREATE TABLE users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," + 
                    "username VARCHAR(50) NOT NULL UNIQUE," + 
                    "password VARCHAR(255) NOT NULL" + 
                    ");";
            
            stmt.executeUpdate(sqlTodosTable);
            stmt.executeUpdate(sqlUsersTable);
            
            System.out.println("Database initialized successfully!");
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
  
}
