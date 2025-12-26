/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

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
    
    
    
    
}
