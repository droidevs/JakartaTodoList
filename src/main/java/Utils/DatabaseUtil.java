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
    
    private static final String URL = "jdbc:mysql://localhost:3306/db_todo_list?zeroDateTimeBehavior=CONVERT_TO_NULL";
    
    private static final String USER = "root";
    
    private static final String PASS = "jakarta";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASS);
    }
    
}
