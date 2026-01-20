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
            String dbHost = getEnvOrDefault("DB_HOST", "postgres");
            String dbPort = getEnvOrDefault("DB_PORT", "5432");
            String dbName = getEnvOrDefault("DB_NAME", "db_todo_list");
            String dbUser = getEnvOrDefault("DB_USER", "todouser");
            String dbPassword = getEnvOrDefault("DB_PASSWORD", "todopassword");

            // PostgreSQL configuration
            String jdbcUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", jdbcUrl);
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
