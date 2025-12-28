/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Data.Category;
import Exceptions.ActionDeniedException;
import Exceptions.ResourceAlreadyExistsException;
import Exceptions.ResourceNotFoundException;
import Exceptions.ValidationException;
import Models.CreateCategoryRequest;
import Models.UpdateCategoryRequest;
import java.util.List;

/**
 *
 * @author admin
 */
public interface CategoryService {

    Category create(CreateCategoryRequest request, int userId)
            throws ValidationException, ResourceAlreadyExistsException;

    Category update(UpdateCategoryRequest request, int userId)
            throws ValidationException, ResourceNotFoundException;

    List<Category> getAll(int userId);

    void delete(int id, int userId) throws ActionDeniedException;
}

