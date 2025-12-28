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

    public String getPath() { return path; }
    public HttpMethod getMethod() { return method; }

    // --- AUTH ---
    public static final Api AUTH_LOGIN       = new Api(Paths.Auth.LOGIN(), HttpMethod.POST);
    public static final Api AUTH_REGISTER    = new Api(Paths.Auth.REGISTER(), HttpMethod.POST);
    public static final Api AUTH_LOGOUT      = new Api(Paths.Auth.LOGOUT(), HttpMethod.POST);

    // --- USERS ---
    public static final Api USERS_PROFILE         = new Api(Paths.Users.PROFILE(), HttpMethod.GET);
    public static final Api USERS_UPDATE_PROFILE  = new Api(Paths.Users.UPDATE_PROFILE(), HttpMethod.POST);
    public static final Api USERS_DELETE_PROFILE  = new Api(Paths.Users.DELETE_PROFILE(), HttpMethod.POST);

    // --- TODOS ---
    public static final Api TODOS_LIST   = new Api(Paths.Todos.LIST(), HttpMethod.GET);
    public static final Api TODOS_CREATE = new Api(Paths.Todos.CREATE(), HttpMethod.POST);

    public static Api TODOS_GET_ONE(int id) {
        return new Api(Paths.Todos.GET_ONE(String.valueOf(id)), HttpMethod.GET);
    }

    public static Api TODOS_UPDATE(int id) {
        return new Api(Paths.Todos.UPDATE(String.valueOf(id)), HttpMethod.POST);
    }

    public static Api TODOS_DELETE(int id) {
        return new Api(Paths.Todos.DELETE(String.valueOf(id)), HttpMethod.POST);
    }

    // --- CATEGORIES ---
    public static final Api CATEGORIES_LIST   = new Api(Paths.Categories.LIST(), HttpMethod.GET);
    public static final Api CATEGORIES_CREATE = new Api(Paths.Categories.CREATE(), HttpMethod.POST);

    public static Api CATEGORIES_GET_ONE(int id) {
        return new Api(Paths.Categories.GET_ONE(String.valueOf(id)), HttpMethod.GET);
    }

    public static Api CATEGORIES_UPDATE(int id) {
        return new Api(Paths.Categories.UPDATE(String.valueOf(id)), HttpMethod.POST);
    }

    public static Api CATEGORIES_DELETE(int id) {
        return new Api(Paths.Categories.DELETE(String.valueOf(id)), HttpMethod.POST);
    }
}

