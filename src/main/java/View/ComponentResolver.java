/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author admin
 */

public final class ComponentResolver {

    private static final String BASE_PATH = "/WEB-INF/components/";
    private static final String EXT = ".jsp";

    // Components
    public static final String HEADER = BASE_PATH + "header" + EXT;
    public static final String FOOTER = BASE_PATH + "footer" + EXT;
    public static final String TODO_ITEM = BASE_PATH + "todo_item" + EXT;
    public static final String CATEGORY_ITEM = BASE_PATH + "category_item" + EXT;

    public static String resolve(String componentName) {
        if (componentName == null || componentName.isEmpty()) {
            throw new IllegalArgumentException("Component name cannot be null or empty");
        }
        return BASE_PATH + componentName + EXT;
    }

    private ComponentResolver() {}
}

