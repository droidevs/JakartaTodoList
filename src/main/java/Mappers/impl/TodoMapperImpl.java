/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers.impl;

import Data.Todo;
import Data.User;
import Mappers.TodoMapper;
import Models.CreateTodoRequest;
import Models.UpdateTodoRequest;

/**
 *
 * @author admin
 */
public class TodoMapperImpl implements TodoMapper {

    @Override
    public Todo toEntity(CreateTodoRequest request, User user) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setStatus(request.getStatus());
        todo.setDueDate(request.getDueDate());
        todo.setUser(user);
        return todo;
    }

    @Override
    public void updateEntity(Todo todo, UpdateTodoRequest request) {
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            todo.setStatus(request.getStatus());
        }

        if (request.getDueDate() != null) {
            todo.setDueDate(request.getDueDate());
        }
    }
    
}
