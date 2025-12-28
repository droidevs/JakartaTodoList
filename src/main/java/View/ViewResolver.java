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

    private static final String COMPONENTS_BASE_PATH = "/WEB-INF/components/";
    
    // Static references for all your JSP pages
    public static final String INDEX = BASE_PATH + "index.jsp";
    public static final String LOGIN = BASE_PATH + "login.jsp";
    public static final String SIGNUP = BASE_PATH + "signup.jsp";
    public static final String LOGOUT = BASE_PATH + "logout.jsp";
    public static final String TODOS = BASE_PATH + "todos.jsp";
    public static final String TODO_FORM = BASE_PATH + "todo_form.jsp";
    public static final String TODO_VIEW = BASE_PATH + "todo_view.jsp";
    public static final String ERROR = BASE_PATH + "error.jsp";
    public static final String HEADER = COMPONENTS_BASE_PATH + "header.jsp";
    public static final String FOOTER = COMPONENTS_BASE_PATH + "footer.jsp";
    public static final String TODO_ITEM = COMPONENTS_BASE_PATH + "todo_item.jsp";


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

