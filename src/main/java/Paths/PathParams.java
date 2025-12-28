/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paths;

/**
 *
 * @author admin
 */
public final class PathParams {
    
    private PathParams() {} // prevent instantiation

    // TODOS
    public static final class Todos {
        public static final String ID = "todoId";
    }

    // CATEGORIES
    public static final class Categories {
        public static final String ID = "categoryId";
    }

    // USERS
    public static final class Users {
        public static final String ID = "userId"; // optional if you ever have IDs
    }

    // AUTH (usually no path params, just POST endpoints)
}

