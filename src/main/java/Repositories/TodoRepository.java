/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;


import Data.Todo;
import java.util.List;
/**
 *
 * @author Mouad OUMOUS
 */
public interface TodoRepository {
    
    
    /**
     * Find a todo by its ID.
     * @param id the todo ID
     * @RETURN the Todo object or null if not found
     */
    Todo findById(int id);
    
    
    /**
     * Find all todos for a specific user.
     * @param userId the user's ID
     * @return list of todos
     */
    List<Todo> findByUserId(int userId);
    
    /**
     * Save a new todo in the database.
     * @param todo the Todo object
     */
    void save(Todo todo);
    
     /**
     * Update an existing todo.
     * @param todo the Todo object
     */
    void update(Todo todo);
    /**
     * Delete a todo by ID.
     * @param id the todo ID
     */
    void delete(int id);
    
    Integer getUserIdForTodo(Integer todoId);
    
    void markOverdueTodos();
}
