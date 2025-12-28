/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Data.Category;
import Data.User;
import Exceptions.ActionDeniedException;
import Exceptions.ResourceAccessDeniedException;
import Exceptions.ResourceAlreadyExistsException;
import Exceptions.ResourceNotFoundException;
import Exceptions.ValidationException;
import Mappers.CategoryMapper;
import Mappers.impl.CategoryMapperImpl;
import Models.CreateCategoryRequest;
import Models.UpdateCategoryRequest;
import Repositories.CategoryRepository;
import Repositories.UserRepository;
import Repositories.impl.CategoryRepositoryHibernete;
import Repositories.impl.UserRepositoryHibernete;
import Validators.RequestValidator;
import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl() {
        this.categoryRepository = new CategoryRepositoryHibernete();
        this.userRepository = new UserRepositoryHibernete();
        this.categoryMapper = new CategoryMapperImpl();
    }

    @Override
    public Category create(CreateCategoryRequest request, int userId)
            throws ValidationException, ResourceAlreadyExistsException {

        RequestValidator.validate(request);

        /*
        if (categoryRepository.findByName(request.getName()) != null) {
            throw new ResourceAlreadyExistsException();
        }*/
        if (categoryRepository.existsByNameAndUser(request.getName(), userId)) {
            throw new ResourceAlreadyExistsException();
        }

        User user = userRepository.findById(userId);

        Category category = categoryMapper.toEntity(request, user);

        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category update(UpdateCategoryRequest request, int userId)
            throws ValidationException, ResourceNotFoundException {

        RequestValidator.validate(request);

        /*
        Category category = categoryRepository.findById(request.getId());
         */
        Category category
                = categoryRepository.findByIdAndUser(request.getId(), userId);

        if (category == null) {
            throw new ResourceNotFoundException();
        }

        categoryMapper.updateEntity(category, request);
        categoryRepository.update(category);
        return category;
    }

    @Override
    public List<Category> getAll(int userId) {
        return categoryRepository.findAll(userId);
    }

    @Override
    public void delete(int id, int userId) {

        Category category
                = categoryRepository.findByIdAndUser(id, userId);

        if (category == null) {
            throw new ResourceAccessDeniedException();
        }
        categoryRepository.delete(id);
    }
}
