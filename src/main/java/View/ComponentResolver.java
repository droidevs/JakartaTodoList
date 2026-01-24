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

    // Components logical names (no base path / extension)
    public static final String HEADER = "header";
    public static final String FOOTER = "footer";
    public static final String TODO_ITEM = "todo_item";
    public static final String CATEGORY_ITEM = "category_item";

    public static String resolve(String componentName) {
        if (componentName == null || componentName.isEmpty()) {
            throw new IllegalArgumentException("Component name cannot be null or empty");
        }
        return BASE_PATH + componentName + EXT;
    }

    private ComponentResolver() {}
}
