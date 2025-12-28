/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Data.Category;
import java.util.List;

/**
 *
 * @author admin
 */
public interface CategoryRepository {
    
    Category findById(int id);

    Category findByName(String name);

    List<Category> findAll();

    void save(Category category);

    void update(Category category);

    void delete(int id);
}
