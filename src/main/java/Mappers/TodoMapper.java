/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Mappers;

import Data.Todo;
import Data.User;
import Models.CreateTodoRequest;
import Models.UpdateTodoRequest;

/**
 *
 * @author admin
 */
public interface TodoMapper {
    
    Todo toEntity(CreateTodoRequest request, User user);
    
    void updateEntity(Todo todo, UpdateTodoRequest request);
   
}
