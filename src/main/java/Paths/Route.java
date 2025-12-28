/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paths;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author admin
 *
 * this class is used by servlets to filter paths and detect actions by path
 * pattern
 */
public enum Route {

    // --- AUTH ---
    AUTH_LOGIN("Login", HttpMethod.POST, BasePaths.AUTH + "/login"),
    AUTH_REGISTER("Register", HttpMethod.POST, BasePaths.AUTH + "/register"),
    AUTH_LOGOUT("Logout", HttpMethod.POST, BasePaths.AUTH + "/logout"),
    // --- USERS ---
    USERS_PROFILE("Profile", HttpMethod.GET, BasePaths.USERS + "/me"),
    USERS_UPDATE_PROFILE("Update Profile", HttpMethod.POST, BasePaths.USERS + "/me/update"),
    USERS_DELETE_PROFILE("Delete Profile", HttpMethod.POST, BasePaths.USERS + "/me/delete"),
    // --- TODOS ---
    TODOS_LIST("List Todos", HttpMethod.GET, BasePaths.TODOS),
    TODOS_CREATE("Create Todo", HttpMethod.POST, BasePaths.TODOS + "/create"),
    TODOS_CREATE_FORM("Create Todo Form", HttpMethod.GET, BasePaths.TODOS + "/create"),
    TODOS_GET_ONE("Get Todo", HttpMethod.GET, BasePaths.TODOS + "/{" + PathParams.Todos.ID + "}"),
    TODOS_UPDATE("Update Todo", HttpMethod.POST, BasePaths.TODOS + "/{" + PathParams.Todos.ID + "}/update"),
    TODOS_EDIT_FORM("Update Todo Form", HttpMethod.GET, BasePaths.TODOS + "/{" + PathParams.Todos.ID + "}/update"),
    TODOS_DELETE("Delete Todo", HttpMethod.POST, BasePaths.TODOS + "/{" + PathParams.Todos.ID + "}/delete"),
    // --- CATEGORIES ---
    CATEGORIES_LIST("List Categories", HttpMethod.GET, BasePaths.CATEGORIES),
    CATEGORIES_CREATE("Create Category", HttpMethod.POST, BasePaths.CATEGORIES),
    CATEGORIES_GET_ONE("Get Category", HttpMethod.GET, BasePaths.CATEGORIES + "/{" + PathParams.Categories.ID + "}"),
    CATEGORIES_UPDATE("Update Category", HttpMethod.POST, BasePaths.CATEGORIES + "/{" + PathParams.Categories.ID + "}/update"),
    CATEGORIES_DELETE("Delete Category", HttpMethod.POST, BasePaths.CATEGORIES + "/{" + PathParams.Categories.ID + "}/delete");

    private final String name;
    private final HttpMethod method;
    private final String path;

    Route(String name, HttpMethod method, String path) {
        this.name = name;
        this.method = method;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
