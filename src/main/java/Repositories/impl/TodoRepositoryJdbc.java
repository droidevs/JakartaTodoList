/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import Constants.TodoStatus;
import Utils.DatabaseUtil;
import Repositories.TodoRepository;
import Data.Todo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mouad OUMOUS
 */
@Deprecated
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
        try(Connection conn = DatabaseUtil.getConnection()) {
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
        try(Connection conn = DatabaseUtil.getConnection()) {
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
        String sql = "INSERT INTO todos (title, description, user_id, status, due_date) VALUES (?, ?, ?,?,?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setInt(3, todo.getUserId());
            ps.setString(4, todo.getStatus().name());
            if (todo.getDueDate() != null) {
                ps.setDate(5, Date.valueOf(todo.getDueDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }
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
        String sql = "UPDATE todos SET title = ?, description = ?, status = ?, due_date = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setString(3, todo.getStatus().name());
            if(todo.getDueDate() != null) {
                ps.setDate(4, Date.valueOf(todo.getDueDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setInt(3, todo.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating todo", e);
        }
    }
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
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
        String statusStr = rs.getString("status");
        todo.setStatus(statusStr != null ? TodoStatus.valueOf(statusStr) : TodoStatus.NEW);
        
        Date due = rs.getDate("due_date");
        if (due != null) {
            todo.setDueDate(due.toLocalDate());
        }
        return todo;
    }

    @Override
    public Integer getUserIdForTodo(Integer todoId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void markOverdueTodos() {
        String MARK_OVERDUE_SQL =
        "UPDATE todos " +
        "SET status = 'OVERDUE' " +
        "WHERE due_date < CURDATE() " +
        "AND status NOT IN ('COMPLETED', 'OVERDUE')";
        
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(MARK_OVERDUE_SQL)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to mark overdue todos", e);
        }
    }
    
}
