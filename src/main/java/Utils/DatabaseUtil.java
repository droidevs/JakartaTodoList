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
        String dbType = getEnvOrDefault("DB_TYPE", "postgres"); // "postgres" or "sqlserver"
        String dbHost = getEnvOrDefault("DB_HOST", "postgres");
        String dbPort = getEnvOrDefault("DB_PORT", "5432");
        String dbName = getEnvOrDefault("DB_NAME", "db_todo_list");
        String dbUser = getEnvOrDefault("DB_USER", "todouser");
        String dbPassword = getEnvOrDefault("DB_PASSWORD", "todopassword");

        String url;
        String driverClass;

        if ("sqlserver".equalsIgnoreCase(dbType)) {
            // Azure SQL Server configuration (production)
            // Include user and password in URL for Azure SQL compatibility
            url = "jdbc:sqlserver://" + dbHost + ":" + dbPort
                    + ";database=" + dbName
                    + ";user=" + dbUser
                    + ";password=" + dbPassword
                    + ";encrypt=true;trustServerCertificate=false"
                    + ";hostNameInCertificate=*.database.windows.net"
                    + ";loginTimeout=30";
            driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

            System.out.println("Connecting to Azure SQL: " + dbHost + "/" + dbName + " as " + dbUser);

            try {
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Database driver not found: " + driverClass, e);
            }

            return DriverManager.getConnection(url);
        } else {
            // PostgreSQL configuration (local development)
            url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
            driverClass = "org.postgresql.Driver";

            try {
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Database driver not found: " + driverClass, e);
            }

            return DriverManager.getConnection(url, dbUser, dbPassword);
        }
    }
    
}
