/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TodoServlet;

import Data.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoStore {
    
    
    public static Integer getUserIdForTodo(Integer todoId) {
        return -1;
    }
    
    public static Todo getTodo(Integer id) {
        String sql = "SELECT * FROM todos WHERE id=?";
        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(0, id);
            ResultSet rs = stmt.executeQuery();
            Todo todo = new Todo();
            todo.setId(rs.getInt("id"));
            todo.setTitle(rs.getString("title"));
            todo.setDescription(rs.getString("description"));
            return todo;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Add a todo
    public static void addTodo(Todo todo) {
        try(Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO todos (user_id, title, description) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, todo.getId());
            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateTodo(Todo todo) {
        String sql = "UPDATE todos SET title=?, description=? WHERE id=?";
        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.setInt(3, todo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteTodo(Integer id) {
        String sql = "DELETE FROM todos WHERE id=?";
        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(0, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all todos
    public static List<Todo> getTodos(Integer userId) {
        List<Todo> todos = new ArrayList<>();
        try(Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM todos WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setTitle(rs.getString("title"));
                todo.setDescription(rs.getString("description"));
                todos.add(todo);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    // Optional: clear all todos
    public static void clearTodos(Integer userId) {
        String sql = "DELETE FROM todos WHERE user_id = ?";
        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.execute(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

