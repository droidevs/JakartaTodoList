/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author admin
 */
public class HiberneteUtil {
    
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration();
            
            // Read database configuration from environment variables
            String dbType = getEnvOrDefault("DB_TYPE", "postgres"); // "postgres" or "sqlserver"
            String dbHost = getEnvOrDefault("DB_HOST", "postgres");
            String dbPort = getEnvOrDefault("DB_PORT", "5432");
            String dbName = getEnvOrDefault("DB_NAME", "db_todo_list");
            String dbUser = getEnvOrDefault("DB_USER", "todouser");
            String dbPassword = getEnvOrDefault("DB_PASSWORD", "todopassword");

            String jdbcUrl;
            if ("sqlserver".equalsIgnoreCase(dbType)) {
                // Azure SQL Server configuration (production)
                jdbcUrl = "jdbc:sqlserver://" + dbHost + ":" + dbPort
                        + ";database=" + dbName
                        + ";user=" + dbUser
                        + ";password=" + dbPassword
                        + ";encrypt=true;trustServerCertificate=false"
                        + ";hostNameInCertificate=*.database.windows.net"
                        + ";loginTimeout=30";
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
                configuration.setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                configuration.setProperty("hibernate.connection.url", jdbcUrl);

                System.out.println("Hibernate connecting to Azure SQL: " + dbHost + "/" + dbName + " as " + dbUser);
            } else {
                // PostgreSQL configuration (local development)
                jdbcUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url", jdbcUrl);

                System.out.println("Hibernate connecting to Postgres: " + jdbcUrl + " as " + dbUser);
            }

            configuration.setProperty("hibernate.connection.username", dbUser);
            configuration.setProperty("hibernate.connection.password", dbPassword);
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");

            // Add XML mappings
            configuration.addResource("mappings/User.hbm.xml");
            configuration.addResource("mappings/Todo.hbm.xml");
            configuration.addResource("mappings/Category.hbm.xml");

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    /**
     * Get environment variable or return default value
     */
    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
