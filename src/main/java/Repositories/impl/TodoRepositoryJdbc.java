/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import Data.Database;
import Repositories.TodoRepository;
import TodoServlet.Todo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class TodoRepositoryJdbc implements TodoRepository {

    private static TodoRepository INSTANCE = null;

    public TodoRepository getInstance() {
        if(INSTANCE != null)
            return INSTANCE;
        else {
            INSTANCE = new TodoRepositoryJdbc();
            return INSTANCE;
        }
    }
    
    @Override
    public Todo findById(int id) {
        String sql = "SELECT * FROM todos WHERE id=?";
        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Todo todo = mapTodo(rs);
            return todo;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Todo> findByUserId(int userId) {
        List<Todo> todos = new ArrayList<>();
        try(Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM todos WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Todo todo = mapTodo(rs);
                todos.add(todo);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    @Override
    public void save(Todo todo) {
        String sql = "INSERT INTO todos (title, description, user_id) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setInt(3, todo.getUserId());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                todo.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving todo", e);
        }
    }

    @Override
    public void update(Todo todo) {
        String sql = "UPDATE todos SET title = ?, description = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setInt(3, todo.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating todo", e);
        }
    }
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting todo", e);
        }
    }
    
    private Todo mapTodo(ResultSet rs) throws SQLException {
        Todo todo = new Todo();
        todo.setId(rs.getInt("id"));
        todo.setTitle(rs.getString("title"));
        todo.setDescription(rs.getString("description"));
        todo.setUserId(rs.getInt("user_id"));
        return todo;
    }

    @Override
    public Integer getUserIdForTodo(Integer todoId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
