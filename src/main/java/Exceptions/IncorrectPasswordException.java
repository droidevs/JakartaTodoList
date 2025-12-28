/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author Mouad OUMOUS
 */
public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException() {
        super("Invalid User or Password Exception");
    }
  
}
