/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;

import Data.User;

/**
 *
 * @author admin
 */
public interface UserRepository {
    
    User findByUsername(String username);
    
    User findById(int id);
    
    void save(User user);
}
