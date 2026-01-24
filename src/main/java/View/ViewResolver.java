/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author admin
 */

/**
 * ViewResolver is a utility class that resolves JSP paths and provides static references
 * to commonly used pages for convenient access.
 */

public final class ViewResolver {

    // Centralized base path and extension
    private static final String BASE_PATH = "/WEB-INF/views/";
    private static final String EXT = ".jsp";

    // Static logical names for views (no base path / extension)
    public static final String INDEX = "index";
    public static final String LOGIN = "login";
    public static final String SIGNUP = "signup";
    public static final String LOGOUT = "logout";
    public static final String TODOS = "todos";
    public static final String TODO_FORM = "todo_form";
    public static final String TODO_VIEW = "todo_view";
    public static final String ERROR = "error";
    public static final String CATEGORIES = "categories";
    public static final String CATEGORY_FORM = "category_form";
    public static final String CATEGORY_VIEW = "category_view";

    /**
     * Resolves a logical view name dynamically, e.g., "todos" -> "/WEB-INF/views/todos.jsp"
     */
    public static String resolve(String viewName) {
        if (viewName == null || viewName.isEmpty()) {
            throw new IllegalArgumentException("View name cannot be null or empty");
        }
        return BASE_PATH + viewName + EXT;
    }

    private ViewResolver() {} // Prevent instantiation
}
