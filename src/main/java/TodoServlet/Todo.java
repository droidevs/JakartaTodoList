/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TodoServlet;

/**
 *
 * @author admin
 */
public class Todo {
    
    private Integer id;
    private String title;
    private String description;
    
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
    
    private static Integer ID = 1;
}
