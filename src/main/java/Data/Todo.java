/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import Constants.TodoStatus;
import java.time.LocalDate;

/**
 *
 * @author Mouad OUMOUS
 */
public class Todo {
    
    private Integer id;
    private String title;
    private String description;
    private Integer userId;
    private TodoStatus status;
    private LocalDate dueDate;

    public Todo() {
        id = -1;
        title = "";
        description = "";
    }
    
    public Todo(String title, String description) {
        id = -1;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }

    public Integer getUserId() {
        return userId;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    
    

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
   
    public Boolean isEmpty() {
        return title.isEmpty() && description.isEmpty();
    }
}
