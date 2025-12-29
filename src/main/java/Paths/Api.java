/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paths;

/**
 *
 * @author admin
 */
public final class Api {

    private final String path;       // Path from Paths class
    private final HttpMethod method;

    private Api(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    // --- AUTH ---
    public static final Api AUTH_LOGIN = new Api(Paths.Auth.LOGIN(), Route.AUTH_LOGIN.getMethod());
    public static final Api AUTH_REGISTER = new Api(Paths.Auth.REGISTER(), Route.AUTH_REGISTER.getMethod());
    public static final Api AUTH_LOGOUT = new Api(Paths.Auth.LOGOUT(), Route.AUTH_LOGOUT.getMethod());

    // --- USERS ---
    public static final Api USERS_PROFILE = new Api(Paths.Users.PROFILE(), Route.USERS_PROFILE.getMethod());
    public static final Api USERS_UPDATE_PROFILE = new Api(Paths.Users.UPDATE_PROFILE(), Route.USERS_UPDATE_PROFILE.getMethod());
    public static final Api USERS_DELETE_PROFILE = new Api(Paths.Users.DELETE_PROFILE(), Route.USERS_DELETE_PROFILE.getMethod());

    // --- TODOS ---
    public static final Api TODOS_LIST = new Api(Paths.Todos.LIST(), Route.TODOS_LIST.getMethod());
    public static final Api TODOS_CREATE = new Api(Paths.Todos.CREATE(), Route.TODOS_CREATE.getMethod());

    public static final Api TODOS_CREATE_FORM = new Api(Paths.Todos.CREATE(), Route.TODOS_CREATE_FORM.getMethod());

    public static Api TODOS_GET_ONE(int id) {
        return new Api(Paths.Todos.GET_ONE(String.valueOf(id)), Route.TODOS_GET_ONE.getMethod());
    }

    public static Api TODOS_UPDATE(int id) {
        return new Api(Paths.Todos.UPDATE(String.valueOf(id)), Route.TODOS_UPDATE.getMethod());
    }

    public static Api TODOS_UPDATE_FORM(int id) {
        return new Api(Paths.Todos.UPDATE_FORM(String.valueOf(id)), Route.TODOS_EDIT_FORM.getMethod());
    }

    public static Api TODOS_DELETE(int id) {
        return new Api(Paths.Todos.DELETE(String.valueOf(id)), Route.TODOS_DELETE.getMethod());
    }

    // --- CATEGORIES ---
    public static final Api CATEGORIES_LIST = new Api(Paths.Categories.LIST(), Route.CATEGORIES_LIST.getMethod());
    public static final Api CATEGORIES_CREATE = new Api(Paths.Categories.CREATE(), Route.CATEGORIES_CREATE.getMethod());

    public static final Api CATEGORIES_CREATE_FORM = new Api(Paths.Categories.CREATE_FORM(), Route.CATEGORIES_CREATE_FORM.getMethod());

    public static Api CATEGORIES_GET_ONE(int id) {
        return new Api(Paths.Categories.GET_ONE(String.valueOf(id)), Route.CATEGORIES_GET_ONE.getMethod());
    }

    public static Api CATEGORIES_UPDATE(int id) {
        return new Api(Paths.Categories.UPDATE(String.valueOf(id)), Route.CATEGORIES_UPDATE.getMethod());
    }
    
    public static Api CATEGORY_EDIT_FORM(int id) {
        return new Api(Paths.Categories.EDIT_FORM(String.valueOf(id)), Route.CATEGORIES_EDIT_FORM.getMethod());
    }

    public static Api CATEGORIES_DELETE(int id) {
        return new Api(Paths.Categories.DELETE(String.valueOf(id)), Route.CATEGORIES_DELETE.getMethod());
    }
}
