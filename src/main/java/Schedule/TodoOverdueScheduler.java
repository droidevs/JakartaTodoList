/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule;

import Services.TodoService;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

/**
 *
 * @author admin
 */

@Singleton
@Startup
public class TodoOverdueScheduler {
    
    private final TodoService todoService = new TodoService();
    
    @Schedule(hour = "0", minute = "0", second = "0", persistent= false)
    public void markOverdueTodos() {
        todoService.markOverdueTodos();
    }
}
