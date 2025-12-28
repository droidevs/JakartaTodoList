/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Data.Category;
import Data.User;
import Models.CreateCategoryRequest;
import Models.UpdateCategoryRequest;

/**
 *
 * @author admin
 */
public interface CategoryMapper {

    Category toEntity(CreateCategoryRequest request, User user);

    void updateEntity(Category c, UpdateCategoryRequest request);
    
}

