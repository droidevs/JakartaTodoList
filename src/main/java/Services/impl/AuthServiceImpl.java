/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import Data.User;
import Exceptions.IncorrectPasswordException;
import Exceptions.PasswordNotConfirmedException;
import Exceptions.UserAlreadyExistsException;
import Exceptions.UserNotFoundException;
import Models.LoginRequest;
import Models.RegisterRequest;
import Repositories.UserRepository;
import Repositories.impl.UserRepositoryHibernete;
//import Repositories.impl.UserRepositoryJdbc;
import Services.AuthService;
import Utils.PasswordUtil;

/**
 *
 * @author Mouad OUMOUS
 */
public class AuthServiceImpl implements AuthService {
    
    private final UserRepository userRepository;

    public AuthServiceImpl() {
        //this.userRepository = new UserRepositoryJdbc();
        this.userRepository = new UserRepositoryHibernete();
    }
    
    @Override
    public User login(LoginRequest request) throws UserNotFoundException, IncorrectPasswordException {
        String username = request.getUsername();
        String password = request.getPassword();
        
        User user = userRepository.findByUsername(username);

        if (user != null) {
            String hashedPassword = user.getPasswordHash();
            if (PasswordUtil.verifyPassword(password, hashedPassword)) {
                // Password correct
                return user;
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            // invalid credentals
            throw new UserNotFoundException();
        }
       
    }
    
    @Override
    public User register(RegisterRequest request) throws UserAlreadyExistsException, PasswordNotConfirmedException {
        String username = request.getUsername();
        String fullName = request.getFullName();
        String password = request.getPassword();
        String confirmPassowrd = request.getConfirmPassword();

        System.out.println("AuthServiceImpl.register: attempting to register username='" + username + "', fullName='" + fullName + "'");

        if(userRepository.findByUsername(username) != null) {
            System.out.println("AuthServiceImpl.register: user already exists: " + username);
            throw new UserAlreadyExistsException();
        }
        
        if (password == null ? confirmPassowrd != null : !password.equals(confirmPassowrd)) {
            System.out.println("AuthServiceImpl.register: password confirmation mismatch for user: " + username);
            throw new PasswordNotConfirmedException();
        }
        
        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPasswordHash(hashedPassword);

        // Save and log
        System.out.println("AuthServiceImpl.register: saving user to repository: " + username);
        userRepository.save(user);
        System.out.println("AuthServiceImpl.register: user saved with id=" + user.getId());
        return user;
    }
    
}
