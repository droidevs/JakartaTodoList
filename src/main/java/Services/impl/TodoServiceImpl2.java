/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import Constants.TodoStatus;
import Data.Todo;
import Data.User;
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
import Repositories.UserRepository;
import Repositories.impl.TodoRepositoryHibernete;
import Repositories.impl.UserRepositoryHibernete;
import Services.TodoService;
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
public class TodoServiceImpl2 implements TodoService {
    
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl2() {
        this.todoRepository = new TodoRepositoryHibernete();
        this.userRepository = new UserRepositoryHibernete();
    }

    
    @Override
    public List<Todo> getTodos(Integer userId) {
        return todoRepository.findByUserId(userId);
    }

    public Todo getTodo(GetTodoRequest request, Integer sessionUserId)
            throws ResourceAccessDeniedException, ResourceNotFoundException {

        Todo todo = todoRepository.findById(request.getId());

        if (todo == null) {
            throw new ResourceNotFoundException();
        }

        if (!Objects.equals(todo.getUser().getId(), sessionUserId)) {
            throw new ResourceAccessDeniedException();
        }

        return todo;
    }

    
    @Override
    public Todo createTodo(CreateTodoRequest request, Integer sessionUserId)
            throws InvalidDueDateException,
                   ArgumentRequiredException,
                   TodoValidationException,
                   ResourceAccessDeniedException {

        validateDueDate(request.getDueDate());

        User user = userRepository.findById(sessionUserId);
        if (user == null) {
            throw new ResourceAccessDeniedException();
        }

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setStatus(request.getStatus());
        todo.setDueDate(request.getDueDate());
        todo.setUser(user);

        TodoValidator.validate(todo);

        todoRepository.save(todo);
        return todo;
    }

    
    @Override
    public Todo updateTodo(UpdateTodoRequest request, Integer sessionUserId)
            throws ResourceAccessDeniedException,
                   ActionDeniedException,
                   InvalidDueDateException,
                   ArgumentRequiredException,
                   TodoValidationException,
                   ResourceNotFoundException {

        Todo todo = todoRepository.findById(request.getId());

        if (todo == null) {
            throw new ResourceNotFoundException();
        }

        if (!Objects.equals(todo.getUser().getId(), sessionUserId)) {
            throw new ResourceAccessDeniedException();
        }

        if (todo.getStatus() == TodoStatus.OVERDUE &&
            !Objects.equals(todo.getDueDate(), request.getDueDate())) {
            throw new ActionDeniedException();
        }

        validateStatusTransition(
                todo.getStatus(),
                request.getStatus(),
                request.getDueDate()
        );

        validateDueDate(request.getDueDate());

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());

        if (todo.getStatus() != TodoStatus.OVERDUE) {
            todo.setDueDate(request.getDueDate());
        }

        todo.setStatus(request.getStatus());

        TodoValidator.validate(todo);

        todoRepository.update(todo);
        return todo;
    }

    
    @Override
    public void deleteTodo(DeleteTodoRequest request, Integer sessionUserId)
            throws ActionDeniedException {

        Todo todo = todoRepository.findById(request.getId());

        if (todo == null ||
            !Objects.equals(todo.getUser().getId(), sessionUserId)) {
            throw new ActionDeniedException();
        }

        todoRepository.delete(todo.getId());
    }

    
    @Override
    public void updateTodoStatus(Integer todoId, Integer userId, TodoStatus status)
            throws ActionDeniedException, ResourceNotFoundException {

        Todo todo = todoRepository.findById(todoId);

        if (todo == null) {
            throw new ResourceNotFoundException();
        }

        if (!Objects.equals(todo.getUser().getId(), userId)) {
            throw new ActionDeniedException();
        }

        validateStatusTransition(todo.getStatus(), status, todo.getDueDate());

        todo.setStatus(status);
        todoRepository.update(todo);
    }

    
    @Override
    public void markOverdueTodos(Integer userId) {
        List<Todo> todos = getTodos(userId);
        LocalDate today = LocalDate.now();

        for (Todo todo : todos) {
            if (todo.getDueDate() != null &&
                todo.getStatus() != TodoStatus.COMPLETED &&
                todo.getDueDate().isBefore(today)) {

                todo.setStatus(TodoStatus.OVERDUE);
                todoRepository.update(todo);
            }
        }
    }

    @Override
    public void markOverdueTodos() {
        todoRepository.markOverdueTodos();
    }
}
