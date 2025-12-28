/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author admin
 */
public class ResourceAccessDeniedException extends RuntimeException{

    public ResourceAccessDeniedException() {
        super("You are not allowed to access this resource");
    }
    
}
