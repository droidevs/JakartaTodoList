/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validators;

import Constants.TodoStatus;
import Exceptions.ActionDeniedException;
import Exceptions.ArgumentRequiredException;
import Exceptions.InvalidDueDateException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author admin
 */
public class TodoBusinessValidator {
    
    
    public static void validateDueDate(LocalDate dueDate) throws InvalidDueDateException, ArgumentRequiredException{
        if(dueDate == null) {
            throw new ArgumentRequiredException();
        }
        
        if (dueDate.isBefore(LocalDate.now())) {
            throw new InvalidDueDateException();
        }
    }
    
    public static void validateStatusTransition(TodoStatus oldStatus, TodoStatus newStatus, LocalDate oldDueDate) throws ActionDeniedException {
        
        if (oldStatus == TodoStatus.COMPLETED && oldStatus != newStatus) {
            throw new ActionDeniedException();
        }
        
        // Cannot go back to new
        if (newStatus == TodoStatus.NEW && oldStatus != TodoStatus.NEW) {
            throw new ActionDeniedException();
        }
        
        if (oldStatus == TodoStatus.COMPLETED && newStatus == TodoStatus.IN_PROGRESS) {
            throw new ActionDeniedException();
        }
        
        if (oldStatus == TodoStatus.OVERDUE && newStatus == TodoStatus.COMPLETED) {
            if (oldDueDate == null || ChronoUnit.DAYS.between(oldDueDate, LocalDate.now()) < 3) {
                throw new ActionDeniedException();
            }
        }
        
        if (oldStatus == TodoStatus.OVERDUE &&
            (newStatus == TodoStatus.NEW || newStatus == TodoStatus.IN_PROGRESS)) {
            throw new ActionDeniedException();
        }
    }
}
