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
    private static final String COMPONENTS_BASE_PATH = "/WEB-INF/components/";
    private static final String EXT = ".jsp";

    // Static references for views (base path and extension are centralized)
    public static final String INDEX = BASE_PATH + "index" + EXT;
    public static final String LOGIN = BASE_PATH + "login" + EXT;
    public static final String SIGNUP = BASE_PATH + "signup" + EXT;
    public static final String LOGOUT = BASE_PATH + "logout" + EXT;
    public static final String TODOS = BASE_PATH + "todos" + EXT;
    public static final String TODO_FORM = BASE_PATH + "todo_form" + EXT;
    public static final String TODO_VIEW = BASE_PATH + "todo_view" + EXT;
    public static final String ERROR = BASE_PATH + "error" + EXT;
    public static final String CATEGORIES = BASE_PATH + "categories" + EXT;
    public static final String CATEGORY_FORM = BASE_PATH + "category_form" + EXT;
    public static final String CATEGORY_VIEW = BASE_PATH + "category_view" + EXT;
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

