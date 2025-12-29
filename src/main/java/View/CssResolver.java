/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author admin
 */

public final class CssResolver {

    private static final String BASE_PATH = "/assets/css/";
    private static final String EXT = ".css";

    // Example CSS files
    public static final String TODOS = BASE_PATH + "todos" + EXT;
    public static final String TODO_FORM = BASE_PATH + "todo_form" + EXT;
    public static final String CATEGORIES = BASE_PATH + "categories" + EXT;
    public static final String CATEGORY_FORM = BASE_PATH + "category_form" + EXT;
    public static final String CATEGORY_VIEW = BASE_PATH + "category_view" + EXT;
    public static final String HEADER = BASE_PATH + "header" + EXT;

    public static String resolve(String cssName) {
        if (cssName == null || cssName.isEmpty()) {
            throw new IllegalArgumentException("CSS name cannot be null or empty");
        }
        return BASE_PATH + cssName + EXT;
    }

    private CssResolver() {}
}


