/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Constants.TodoStatus;
import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class UpdateTodoRequest {
    
    Integer id;
    String title;
    String description;
    LocalDate dueDate;
    TodoStatus status;

    public UpdateTodoRequest() {
    }

    public UpdateTodoRequest(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public UpdateTodoRequest(Integer id, String title, String description, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public UpdateTodoRequest(Integer id, String title, String description, LocalDate dueDate, TodoStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }
    
    
    

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TodoStatus getStatus() {
        return status;
    }
    
    
    

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }
    
    
}
