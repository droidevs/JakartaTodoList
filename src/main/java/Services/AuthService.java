/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

/**
 *
 * @author admin
 */
import Data.User;
import Exceptions.IncorrectPasswordException;
import Exceptions.PasswordNotConfirmedException;
import Exceptions.UserAlreadyExistsException;
import Exceptions.UserNotFoundException;
import Models.LoginRequest;
import Models.RegisterRequest;

public interface AuthService {

    /**
     * Authenticate a user using username and password.
     *
     * @param request login data
     * @return authenticated User
     * @throws UserNotFoundException if username does not exist
     * @throws IncorrectPasswordException if password is invalid
     */
    User login(LoginRequest request)
            throws UserNotFoundException, IncorrectPasswordException;

    /**
     * Register a new user.
     *
     * @param request registration data
     * @return created User
     * @throws UserAlreadyExistsException if username already exists
     * @throws PasswordNotConfirmedException if passwords do not match
     */
    User register(RegisterRequest request)
            throws UserAlreadyExistsException, PasswordNotConfirmedException;
}

