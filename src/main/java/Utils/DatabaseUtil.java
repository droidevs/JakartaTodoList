/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @deprecated in favor of HibernetUtil
 * @author Mouad OUMOUS
 */
@Deprecated
public class DatabaseUtil {
    
    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }

    public static Connection getConnection() throws SQLException {
        String dbHost = getEnvOrDefault("DB_HOST", "postgres");
        String dbPort = getEnvOrDefault("DB_PORT", "5432");
        String dbName = getEnvOrDefault("DB_NAME", "db_todo_list");
        String dbUser = getEnvOrDefault("DB_USER", "todouser");
        String dbPassword = getEnvOrDefault("DB_PASSWORD", "todopassword");

        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL driver not found", e);
        }

        return DriverManager.getConnection(url, dbUser, dbPassword);
    }
    
}
