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
public class ViewResolver {

    private static final String BASE_PATH = "/WEB-INF/views/";

    // Static references for all your JSP pages
    public static final String INDEX = BASE_PATH + "index.jsp";
    public static final String LOGIN = BASE_PATH + "login.jsp";
    public static final String SIGNUP = BASE_PATH + "signup.jsp";
    public static final String TODOS = BASE_PATH + "todos.jsp";
    public static final String TODO_FORM = BASE_PATH + "TodoForm.jsp";
    public static final String TODO_VIEW = BASE_PATH + "ViewTodo.jsp";
    public static final String ERROR = BASE_PATH + "error.jsp";
    public static final String HEADER = BASE_PATH + "header.jsp";
    public static final String FOOTER = BASE_PATH + "footer.jsp";

    /**
     * Resolves a logical view name dynamically if needed.
     *
     * @param viewName logical view name (without extension)
     * @return full JSP path under /WEB-INF/views/
     */
    public static String resolve(String viewName) {
        if (viewName == null || viewName.isEmpty()) {
            throw new IllegalArgumentException("View name cannot be null or empty");
        }
        return BASE_PATH + viewName + ".jsp";
    }

    // Private constructor to prevent instantiation
    private ViewResolver() {}
}

