/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TodoServlet;

/**
 *
 * @author Mouad OUMOUS
 */
public class Todo {
    
    private Integer id;
    private String title;
    private String description;
    private Integer userId;

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
    
   
    public Boolean isEmpty() {
        return title.isEmpty() && description.isEmpty();
    }
}
