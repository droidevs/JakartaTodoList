/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Listeners;

import Utils.DatabaseUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mouad OUMOUS
 */
@WebListener
public class DatabaseIntializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            Statement stmt = conn.createStatement();

            // Create table if not exists
            String sqlTodosTable = "CREATE TABLE IF NOT EXSTS todos ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "title VARCHAR(255) NOT NULL,"
                    + "description TEXT"
                    + "status VARCHAR(20) NOT NULL DEFAULT 'NEW'"
                    + "due_date DATE"
                    + ");";
            String sqlUsersTable = "CREATE TABLE users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "username VARCHAR(50) NOT NULL UNIQUE,"
                    + "full_name VARCHAR(100) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL"
                    + "FOREIGH KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
                    + ");";

            String sqlCategories = "CREATE TABLE categories ("
                    + "    id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "    name VARCHAR(100) NOT NULL UNIQUE,"
                    + "    color VARCHAR(20) NOT NULL,"
                    + "    description VARCHAR(300)"
                    + ");"
                    + "ALTER TABLE todos"
                    + "ADD COLUMN category_id INT NOT NULL,"
                    + "ADD CONSTRAINT fk_todo_category"
                    + "FOREIGN KEY (category_id) REFERENCES categories(id);";
            
            stmt.executeUpdate(sqlTodosTable);
            stmt.executeUpdate(sqlUsersTable);
            stmt.executeUpdate(sqlCategories);
            
            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
