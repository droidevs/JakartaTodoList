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

            // PostgreSQL syntax
            String sqlUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id SERIAL PRIMARY KEY,"
                    + "username VARCHAR(50) NOT NULL UNIQUE,"
                    + "full_name VARCHAR(100) NOT NULL,"
                    + "password_hash VARCHAR(255) NOT NULL"
                    + ");";

            String sqlCategories = "CREATE TABLE IF NOT EXISTS categories ("
                    + "id SERIAL PRIMARY KEY,"
                    + "name VARCHAR(100) NOT NULL,"
                    + "color VARCHAR(20),"
                    + "description VARCHAR(300),"
                    + "user_id INT NOT NULL,"
                    + "CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id),"
                    + "CONSTRAINT uq_user_category_name UNIQUE (user_id, name)"
                    + ");";

            String sqlTodosTable = "CREATE TABLE IF NOT EXISTS todos ("
                    + "id SERIAL PRIMARY KEY,"
                    + "title VARCHAR(255) NOT NULL,"
                    + "description TEXT,"
                    + "status VARCHAR(20) NOT NULL DEFAULT 'NEW',"
                    + "due_date DATE,"
                    + "user_id INT NOT NULL,"
                    + "category_id INT,"
                    + "CONSTRAINT fk_todo_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,"
                    + "CONSTRAINT fk_todo_category FOREIGN KEY (category_id) REFERENCES categories(id)"
                    + ");";

            stmt.executeUpdate(sqlUsersTable);
            stmt.executeUpdate(sqlCategories);
            stmt.executeUpdate(sqlTodosTable);

            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

}
