/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paths;

/**
 *
 * @author admin
 * 
 * this class is used by api get get paths and map it to coresponding method
 */
public final class Paths {

    private Paths() {} // Prevent instantiation

    // --- AUTH ---
    public static final class Auth {

        public static String LOGIN() {
            return Route.AUTH_LOGIN.getPath();
        }

        public static String REGISTER() {
            return Route.AUTH_REGISTER.getPath();
        }

        public static String LOGOUT() {
            return Route.AUTH_LOGOUT.getPath();
        }
    }

    // --- USERS ---
    public static final class Users {

        public static String PROFILE() {
            return Route.USERS_PROFILE.getPath();
        }

        public static String UPDATE_PROFILE() {
            return Route.USERS_UPDATE_PROFILE.getPath();
        }

        public static String DELETE_PROFILE() {
            return Route.USERS_DELETE_PROFILE.getPath();
        }
    }

    // --- TODOS ---
    public static final class Todos {
        public static final String BASE = Route.TODOS_LIST.getPath();

        public static String LIST() { return Route.TODOS_LIST.getPath(); }
        public static String CREATE() { return Route.TODOS_CREATE.getPath(); }

        public static String GET_ONE(String id) {
            return Route.TODOS_GET_ONE.getPath().replace("{" + PathParams.Todos.ID + "}", id);
        }

        public static String UPDATE(String id) {
            return Route.TODOS_UPDATE.getPath().replace("{" + PathParams.Todos.ID + "}", id);
        }

        public static String DELETE(String id) {
            return Route.TODOS_DELETE.getPath().replace("{" + PathParams.Todos.ID + "}", id);
        }
    }

    // --- CATEGORIES ---
    public static final class Categories {
        public static final String BASE = Route.CATEGORIES_LIST.getPath();

        public static String LIST() { return Route.CATEGORIES_LIST.getPath(); }
        public static String CREATE() { return Route.CATEGORIES_CREATE.getPath(); }

        public static String GET_ONE(String id) {
            return Route.CATEGORIES_GET_ONE.getPath().replace("{" + PathParams.Categories.ID + "}", id);
        }

        public static String UPDATE(String id) {
            return Route.CATEGORIES_UPDATE.getPath().replace("{" + PathParams.Categories.ID + "}", id);
        }

        public static String DELETE(String id) {
            return Route.CATEGORIES_DELETE.getPath().replace("{" + PathParams.Categories.ID + "}", id);
        }
    }
}
