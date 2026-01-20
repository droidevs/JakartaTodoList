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
    
    
    
    private User user;
    
    private Category category;
    
    @Deprecated
    private Integer user_id;
    
    private String status;
    
    
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
    
    /*
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = TodoStatus.NEW;
        }
    }*/

    public Integer getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }
    
    @Deprecated
    public Integer getUserId() {
        return user_id;
    }
    
    @Deprecated
    public void setUserId(Integer id) {
        user_id = id;
    }

    public TodoStatus getStatus() {
        return status != null ? TodoStatus.valueOf(status) : null;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Category getCategory() {
        return category;
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

    public void setStatus(TodoStatus status) {
        this.status = status.name();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    
    
    
   
    public Boolean isEmpty() {
        return title.isEmpty() && description.isEmpty();
    }
}
