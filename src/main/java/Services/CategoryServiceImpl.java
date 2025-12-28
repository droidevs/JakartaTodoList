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
import Mappers.CategoryMapper;
import Models.CreateCategoryRequest;
import Models.UpdateCategoryRequest;
import Repositories.CategoryRepository;
import Validators.RequestValidator;
import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(CreateCategoryRequest request)
            throws ValidationException, ResourceAlreadyExistsException {

        RequestValidator.validate(request);

        if (categoryRepository.findByName(request.getName()) != null) {
            throw new ResourceAlreadyExistsException();
        }

        Category category = CategoryMapper.toEntity(request);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category update(UpdateCategoryRequest request)
            throws ValidationException, ResourceNotFoundException {

        RequestValidator.validate(request);

        Category category = categoryRepository.findById(request.getId());
        if (category == null) {
            throw new ResourceNotFoundException();
        }

        CategoryMapper.updateEntity(category, request);
        categoryRepository.update(category);
        return category;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(int id) throws ActionDeniedException {
        // optional: prevent deletion if used by todos
        categoryRepository.delete(id);
    }
}