/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TodoServlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TodoStore {
    // Static list to store all todos
    private static final List<Todo> todos = new ArrayList<>();

    // Add a todo
    public static void addTodo(Todo todo) {
        todos.add(todo);
    }
    
    public static void deleteTodo(Integer id) {
        todos.removeIf((var todo) -> Objects.equals(id, todo.getId()));
    }

    // Get all todos
    public static List<Todo> getTodos() {
        return new ArrayList<>(todos); // return copy to avoid external modification
    }

    // Optional: clear all todos
    public static void clearTodos() {
        todos.clear();
    }
}

