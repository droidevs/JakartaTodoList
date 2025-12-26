/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author admin
 */
public class ActionDeniedException extends Exception {

    public ActionDeniedException() {
        super("You are not allowed to do this action");
    }
    
}
