/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TodoServlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TodoStore {
    // Static list to store all todos
    private static final Map<Integer, Todo> todos = new LinkedHashMap<>();
    
    public static Todo getTodo(Integer id) {
        return todos.get(id);
    }
    // Add a todo
    public static void addTodo(Todo todo) {
        todos.put(todo.getId(), todo);
    }
    
    public static void deleteTodo(Integer id) {
        todos.remove(id);
    }

    // Get all todos
    public static Collection<Todo> getTodos() {
        return todos.values(); // return copy to avoid external modification
    }

    // Optional: clear all todos
    public static void clearTodos() {
        todos.clear();
    }
}

