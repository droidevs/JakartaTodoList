/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;


import Data.Database;
import Repositories.UserRepository;
import Data.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author admin
 */
public class UserRepositoryJdbc implements UserRepository {
    
    private static UserRepository INSTANCE = null;

    public UserRepository getInstance() {
        if(INSTANCE != null)
            return INSTANCE;
        else {
            INSTANCE = new UserRepositoryJdbc();
            return INSTANCE;
        }
    }
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try(Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error finding user by username", e);
        }
        return null;
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try(Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error finding user by id", e);
        }
        return null;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (username, full_name, password) VALUES (?,?,?)";
        
        try(Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPasswordHash());
            ps.executeUpdate();
            
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getInt(1));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving user", e);
        }
    }
   
    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setPasswordHash(rs.getString("password"));
        return user;
    }
}
