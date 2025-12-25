/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Data.User;
import Exceptions.InvalidPasswordException;
import Exceptions.PasswordNotConfirmedException;
import Exceptions.UserAlreadyExistException;
import Exceptions.UserNotFoundException;
import Models.LoginRequest;
import Models.RegisterRequest;
import Repositories.UserRepository;
import Repositories.impl.UserRepositoryJdbc;
import Utils.PasswordUtil;

/**
 *
 * @author admin
 */
public class AuthService {
    
    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepositoryJdbc();
    }
    
    public User login(LoginRequest request) throws UserNotFoundException, InvalidPasswordException {
        String username = request.getUsername();
        String password = request.getPassword();
        
        User user = userRepository.findByUsername(username);

        if (user != null) {
            String hashedPassword = user.getPasswordHash();
            if (PasswordUtil.verifyPassword(password, hashedPassword)) {
                // Password correct
                return user;
            } else {
                throw new InvalidPasswordException();
            }
        } else {
            // invalid credentals
            throw new UserNotFoundException();
        }
       
    }
    
    public User register(RegisterRequest request) throws UserAlreadyExistException, PasswordNotConfirmedException {
        String username = request.getUsername();
        String fullName = request.getFullName();
        String password = request.getPassword();
        String confirmPassowrd = request.getConfirmPassword();
        
        if(userRepository.findByUsername(username) != null) {
            throw new UserAlreadyExistException();
        }
        
        if (password == null ? confirmPassowrd != null : !password.equals(confirmPassowrd)) {
            throw new PasswordNotConfirmedException();
        }
        
        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);
        return user;
    }
    
}
