/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import Models.CreateTodoRequest;
import Models.DeleteTodoRequest;
import Models.GetTodoRequest;
import Models.UpdateTodoRequest;
import Repositories.TodoRepository;
import Repositories.impl.TodoRepositoryJdbc;
import static Validators.TodoBusinessValidator.validateDueDate;
import static Validators.TodoBusinessValidator.validateStatusTransition;
import Validators.TodoValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author admin
 */
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService() {
        this.todoRepository = new TodoRepositoryJdbc();
    }

    public List<Todo> getTodos(Integer userId) {
        return todoRepository.findByUserId(userId);
    }

    public Todo getTodo(GetTodoRequest request, Integer sessionUser) throws ResourceAccessDeniedException, ResourceNotFoundException {
        Integer id = request.getId();
        var todoUser = todoRepository.getUserIdForTodo(id);
        if (!Objects.equals(todoUser, sessionUser)) {
            throw new ResourceAccessDeniedException();
        }

        Todo todo = todoRepository.findById(id);

        if (todo == null) {
            throw new ResourceNotFoundException();
        }
        return todo;
    }

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(CreateTodoRequest request, Integer sessionUser) throws InvalidDueDateException, ArgumentRequiredException, TodoValidationException {
        Todo todo = new Todo();
        
        validateDueDate(request.getDueDate());
        
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setStatus(request.getStatus());
        todo.setDueDate(request.getDueDate());
        todo.setUserId(sessionUser);
        
        TodoValidator.validate(todo);
        
        todoRepository.save(todo);
        return todo;
    }

    public Todo updateTodo(UpdateTodoRequest request, Integer sessionUser) throws ResourceAccessDeniedException, ActionDeniedException, InvalidDueDateException, ArgumentRequiredException, TodoValidationException {
        Integer id = request.getId();
        var todoUser = todoRepository.getUserIdForTodo(id);
        if (!Objects.equals(todoUser, sessionUser)) {
            throw new ResourceAccessDeniedException();
        }

        var todo = todoRepository.findById(request.getId());
        
        if(todo.getStatus() == TodoStatus.OVERDUE &&
                    !Objects.equals(todo.getDueDate(), request.getDueDate())) {
            throw new ActionDeniedException();
        }
        
        validateStatusTransition(todo.getStatus(), request.getStatus(), request.getDueDate());
        
        validateDueDate(request.getDueDate());
        
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        
        if(todo.getStatus() != TodoStatus.OVERDUE) {
            todo.setDueDate(request.getDueDate());
        }
        
        TodoValidator.validate(todo);

        todoRepository.save(todo);
        return todo;
    }

    public void deleteTodo(DeleteTodoRequest request, Integer sessionUser) throws ActionDeniedException {
        var todoUser = todoRepository.getUserIdForTodo(request.getId());
        if (!Objects.equals(todoUser, sessionUser)) {
            throw new ActionDeniedException();
        }
    }

    public void updateTodoStatus(int todoId, int userId, TodoStatus status) throws ActionDeniedException, ResourceNotFoundException {
        Todo todo;
        try {
            todo = getTodo(new GetTodoRequest(todoId), userId);
            
            validateStatusTransition(todo.getStatus(), status, todo.getDueDate());
            
            todo.setStatus(status);
            todoRepository.update(todo);
        } catch (ResourceAccessDeniedException ex) {
            throw new ActionDeniedException();
        }
    }

    /**
     * auto function
     *
     * @param userId
     */
    public void markOverdueTodos(int userId) {
        List<Todo> todos = getTodos(userId);
        LocalDate today = LocalDate.now();
        for (Todo t : todos) {
            if (t.getDueDate() != null
                    && t.getStatus() != TodoStatus.COMPLETED
                    && t.getDueDate().isBefore(today)) {
                t.setStatus(TodoStatus.OVERDUE);
                todoRepository.update(t);
            }
        }
    }
    
   
    
    public void markOverdueTodos() {
        todoRepository.markOverdueTodos();
    }
}
