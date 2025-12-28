/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers.impl;

import Data.Category;
import Data.User;
import Mappers.CategoryMapper;
import Models.CreateCategoryRequest;
import Models.UpdateCategoryRequest;

/**
 *
 * @author admin
 */
public class CategoryMapperImpl implements CategoryMapper {

    public CategoryMapperImpl() {
    }

    @Override
    public Category toEntity(CreateCategoryRequest request, User user) {
        Category c = new Category();
        c.setName(request.getName());
        c.setColor(request.getColor());
        c.setDescription(request.getDescription());
        c.setUser(user);
        return c;
    }

    @Override
    public void updateEntity(Category c, UpdateCategoryRequest request) {
        c.setName(request.getName());
        c.setColor(request.getColor());
        c.setDescription(request.getDescription());
    }
}
