/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author admin
 */
public class InvalidDueDateException extends RuntimeException {
    
    public InvalidDueDateException() {
        super("Invalid due date");
    }
}
