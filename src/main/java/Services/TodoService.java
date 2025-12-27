/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;




import Constants.TodoStatus;
import Data.Todo;
import Exceptions.ActionDeniedException;
import Exceptions.ArgumentRequiredException;
import Exceptions.InvalidDueDateException;
import Exceptions.ResourceAccessDeniedException;
import Exceptions.ResourceNotFoundException;
import Exceptions.TodoValidationException;
import Exceptions.ValidationException;
import Models.CreateTodoRequest;
import Models.DeleteTodoRequest;
import Models.GetTodoRequest;
import Models.UpdateTodoRequest;
import java.util.List;

/**
 *
 * @author admin
 */


public interface TodoService {

    /* =========================
       READ
       ========================= */

    List<Todo> getTodos(Integer userId);

    Todo getTodo(GetTodoRequest request, Integer sessionUserId)
            throws ResourceAccessDeniedException,
                   ResourceNotFoundException;

    /* =========================
       CREATE
       ========================= */

    Todo createTodo(CreateTodoRequest request, Integer sessionUserId)
            throws InvalidDueDateException,
                   ArgumentRequiredException,
                   //TodoValidationException,
                   ValidationException,
                   ResourceAccessDeniedException;

    /* =========================
       UPDATE
       ========================= */

    Todo updateTodo(UpdateTodoRequest request, Integer sessionUserId)
            throws ResourceAccessDeniedException,
                   ActionDeniedException,
                   InvalidDueDateException,
                   ArgumentRequiredException,
                   //TodoValidationException,
                   ValidationException,
                   ResourceNotFoundException;

    /* =========================
       DELETE
       ========================= */

    void deleteTodo(DeleteTodoRequest request, Integer sessionUserId)
            throws ActionDeniedException;

    /* =========================
       STATUS
       ========================= */

    void updateTodoStatus(Integer todoId, Integer userId, TodoStatus status)
            throws ActionDeniedException,
                   ResourceNotFoundException;

    /* =========================
       AUTO / SCHEDULED
       ========================= */

    /**
     *
     * @param userId
     */
    void markOverdueTodos(Integer userId);

    void markOverdueTodos();
}

