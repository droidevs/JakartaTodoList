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

    public Todo() {
        id = ID++;
        title = "";
        description = "";
    }
    
    public Todo(String title, String description) {
        id = ID++;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
   
    public Boolean isEmpty() {
        return title.isEmpty() && description.isEmpty();
    }
    
    private static Integer ID = 1;
}
