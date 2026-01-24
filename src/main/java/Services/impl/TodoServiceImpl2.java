/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import Constants.TodoStatus;
import Data.Category;
import Data.Todo;
import Data.User;
import Exceptions.ActionDeniedException;
import Exceptions.ArgumentRequiredException;
import Exceptions.InvalidDueDateException;
import Exceptions.ResourceAccessDeniedException;
import Exceptions.ResourceNotFoundException;
import Exceptions.TodoValidationException;
import Exceptions.ValidationException;
import Mappers.TodoMapper;
import Mappers.impl.TodoMapperImpl;
import Models.CreateTodoRequest;
import Models.DeleteTodoRequest;
import Models.GetTodoRequest;
import Models.UpdateTodoRequest;
import Repositories.CategoryRepository;
import Repositories.TodoRepository;
import Repositories.UserRepository;
import Repositories.impl.CategoryRepositoryHibernete;
import Repositories.impl.TodoRepositoryHibernete;
import Repositories.impl.UserRepositoryHibernete;
import Services.TodoService;
import Validators.RequestValidator;
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
    private final CategoryRepository categoryRepository;
    private final TodoMapper todoMapper;

    public TodoServiceImpl2() {
        this.todoRepository = new TodoRepositoryHibernete();
        this.userRepository = new UserRepositoryHibernete();
        this.categoryRepository = new CategoryRepositoryHibernete();
        this.todoMapper = new TodoMapperImpl();
    }

    @Override
    public List<Todo> getTodos(Integer userId) {
        return todoRepository.findByUserId(userId);
    }

    @Override
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
            //TodoValidationException,
            ValidationException,
            ResourceAccessDeniedException {

        System.out.println("TodoServiceImpl2.createTodo: incoming request=" + request + " sessionUserId=" + sessionUserId);

        RequestValidator.validate(request);

        validateDueDate(request.getDueDate());

        User user = userRepository.findById(sessionUserId);
        System.out.println("TodoServiceImpl2.createTodo: found user=" + (user == null ? "null" : user.getId()));
        if (user == null) {
            throw new ResourceAccessDeniedException();
        }

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findByIdAndUser(
                    request.getCategoryId(),
                    sessionUserId
            );
            System.out.println("TodoServiceImpl2.createTodo: looked up category id=" + request.getCategoryId() + " result=" + (category == null ? "null" : category.getId()));
            if (category == null) {
                // if provided categoryId is invalid or not owned by user, treat as no category instead of denying
                System.out.println("TodoServiceImpl2.createTodo: provided categoryId not found or not owned by user, ignoring category");
            }
        } else {
            System.out.println("TodoServiceImpl2.createTodo: no categoryId provided, creating todo without category");
        }

        Todo todo = todoMapper.toEntity(request, user, category);

        // valiate todot
        todoRepository.save(todo);
        return todo;
    }

    @Override
    public Todo updateTodo(UpdateTodoRequest request, Integer sessionUserId)
            throws ResourceAccessDeniedException,
            ActionDeniedException,
            InvalidDueDateException,
            ArgumentRequiredException,
            //TodoValidationException,
            ValidationException,
            ResourceNotFoundException {

        RequestValidator.validate(request);

        Todo todo = todoRepository.findById(request.getId());

        if (todo == null) {
            throw new ResourceNotFoundException();
        }

        if (!Objects.equals(todo.getUser().getId(), sessionUserId)) {
            throw new ResourceAccessDeniedException();
        }

        if (todo.getStatus() == TodoStatus.OVERDUE
                && !Objects.equals(todo.getDueDate(), request.getDueDate())) {
            throw new ActionDeniedException();
        }

        validateStatusTransition(
                todo.getStatus(),
                request.getStatus(),
                request.getDueDate()
        );

        validateDueDate(request.getDueDate());

        if (todo.getStatus() == TodoStatus.OVERDUE) {
            request.setDueDate(null);
        }

        todoMapper.updateEntity(todo, request);

        // Handle category change from edit form. If categoryId is null -> remove category.
        if (request.getCategoryId() == null) {
            todo.setCategory(null);
        } else {
            Category newCategory = categoryRepository.findByIdAndUser(request.getCategoryId(), sessionUserId);
            if (newCategory != null) {
                todo.setCategory(newCategory);
            } else {
                // Provided category is invalid/not owned by user - ignore change (leave as null)
                System.out.println("TodoServiceImpl2.updateTodo: provided categoryId not found or not owned by user, setting no category");
                todo.setCategory(null);
            }
        }

        todo.setStatus(request.getStatus());

        //TodoValidator.validate(todo);
        todoRepository.update(todo);
        return todo;
    }

    @Override
    public void deleteTodo(DeleteTodoRequest request, Integer sessionUserId)
            throws ActionDeniedException {

        Todo todo = todoRepository.findById(request.getId());

        if (todo == null
                || !Objects.equals(todo.getUser().getId(), sessionUserId)) {
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
            if (todo.getDueDate() != null
                    && todo.getStatus() != TodoStatus.COMPLETED
                    && todo.getDueDate().isBefore(today)) {

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
