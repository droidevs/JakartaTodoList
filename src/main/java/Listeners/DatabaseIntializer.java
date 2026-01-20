/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Listeners;

import Utils.DatabaseUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 *
 * @author Mouad OUMOUS
 */
@WebListener
public class DatabaseIntializer implements ServletContextListener {

    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dbType = getEnvOrDefault("DB_TYPE", "postgres");

        try (Connection conn = DatabaseUtil.getConnection()) {
            Statement stmt = conn.createStatement();

            if ("sqlserver".equalsIgnoreCase(dbType)) {
                // Load Azure SQL script from resources
                executeSqlScript(stmt, "sql/02-azure-init.sql", true);
            } else {
                // Load PostgreSQL script from resources
                executeSqlScript(stmt, "sql/01-init.sql", false);
            }

            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Load and execute SQL script from resources
     * @param stmt Statement to execute SQL
     * @param resourcePath Path to SQL file in resources
     * @param isSqlServer Whether this is SQL Server (uses GO as batch separator)
     */
    private void executeSqlScript(Statement stmt, String resourcePath, boolean isSqlServer) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("SQL script not found: " + resourcePath);
                return;
            }

            String sqlContent;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                sqlContent = reader.lines().collect(Collectors.joining("\n"));
            }

            // Remove comments
            sqlContent = removeComments(sqlContent);

            if (isSqlServer) {
                // SQL Server uses GO as batch separator
                String[] batches = sqlContent.split("(?i)\\bGO\\b");
                for (String batch : batches) {
                    batch = batch.trim();
                    if (!batch.isEmpty()) {
                        try {
                            stmt.execute(batch);
                        } catch (SQLException e) {
                            System.err.println("Error executing batch: " + e.getMessage());
                        }
                    }
                }
            } else {
                // PostgreSQL - execute statements separated by semicolons
                String[] statements = sqlContent.split(";");
                for (String statement : statements) {
                    statement = statement.trim();
                    if (!statement.isEmpty()) {
                        try {
                            stmt.execute(statement);
                        } catch (SQLException e) {
                            System.err.println("Error executing statement: " + e.getMessage());
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error loading SQL script: " + resourcePath);
            e.printStackTrace();
        }
    }

    /**
     * Remove SQL comments from content
     */
    private String removeComments(String sql) {
        // Remove single-line comments
        sql = sql.replaceAll("--.*", "");
        // Remove multi-line comments
        sql = sql.replaceAll("/\\*[\\s\\S]*?\\*/", "");
        return sql;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

}
