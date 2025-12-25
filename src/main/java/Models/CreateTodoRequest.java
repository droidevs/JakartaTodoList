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
public class CreateTodoRequest {
    
    String title;
    String description;
    TodoStatus status;
    LocalDate dueDate;

    public CreateTodoRequest() {
    }

    public CreateTodoRequest(String title, String description, TodoStatus status, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }
    
    
    
}
