/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Data.Category;
import Models.CreateCategoryRequest;
import Models.UpdateCategoryRequest;

/**
 *
 * @author admin
 */
public class CategoryMapper {

    public static Category toEntity(CreateCategoryRequest request) {
        Category c = new Category();
        c.setName(request.getName());
        c.setColor(request.getColor());
        c.setDescription(request.getDescription());
        return c;
    }

    public static void updateEntity(Category c, UpdateCategoryRequest request) {
        c.setName(request.getName());
        c.setColor(request.getColor());
        c.setDescription(request.getDescription());
    }
}

