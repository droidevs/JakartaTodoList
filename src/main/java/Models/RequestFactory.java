/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Constants.TodoStatus;
import static Utils.Utils.parseDate;
import static Utils.Utils.parseEnum;
import jakarta.servlet.http.HttpServletRequest;
import static java.lang.Integer.parseInt;
import java.time.LocalDate;

/**
 *
 * @author admin
 */
public final class RequestFactory {

    private RequestFactory() {
    }

    public static CreateTodoRequest createTodo(HttpServletRequest req) {

        String dueDateStr = req.getParameter(TodoParams.DUE_DATE);
        
        LocalDate dueDate = (dueDateStr != null && !dueDateStr.isBlank())
                ? parseDate(dueDateStr)
                : null;
        
        return new CreateTodoRequest(
                req.getParameter(TodoParams.TITLE),
                req.getParameter(TodoParams.DESCRIPTION),
                parseEnum(TodoStatus.class, req.getParameter(TodoParams.STATUS)),
                dueDate,
                parseInt(req.getParameter(TodoParams.CATEGORY_ID))
        );
    }

    public static UpdateTodoRequest updateTodo(HttpServletRequest req, int todoId) {

        return new UpdateTodoRequest(
                todoId,
                req.getParameter(TodoParams.TITLE),
                req.getParameter(TodoParams.DESCRIPTION),
                parseDate(req.getParameter(TodoParams.DUE_DATE)),
                parseEnum(TodoStatus.class, req.getParameter(TodoParams.STATUS)),
                parseInt(req.getParameter(TodoParams.CATEGORY_ID))
        );
    }

    public static GetTodoRequest getTodo(int id) {
        return new GetTodoRequest(id);
    }

    public static DeleteTodoRequest deleteTodo(int id) {
        return new DeleteTodoRequest(id);
    }

    public static CreateCategoryRequest createCategory(HttpServletRequest req) {

        return new CreateCategoryRequest(
                req.getParameter(CategoryParams.NAME),
                req.getParameter(CategoryParams.COLOR),
                req.getParameter(CategoryParams.DESCRIPTION)
        );
    }

    public static UpdateCategoryRequest updateCategory(
            HttpServletRequest req,
            int categoryId
    ) {
        return new UpdateCategoryRequest(
                categoryId,
                req.getParameter(CategoryParams.NAME),
                req.getParameter(CategoryParams.COLOR),
                req.getParameter(CategoryParams.DESCRIPTION)
        );
    }
    
    
    public static GetCategoryRequest getCategory(int id) {
        return new GetCategoryRequest(id);
    }
    
    public static DeleteCategoryRequest deleteCategory(int id) {
        return new DeleteCategoryRequest(id);
    }

    public static LoginRequest login(HttpServletRequest req) {

        return new LoginRequest(
                req.getParameter(AuthParams.USERNAME),
                req.getParameter(AuthParams.PASSWORD)
        );
    }

    public static RegisterRequest register(HttpServletRequest req) {

        return new RegisterRequest(
                req.getParameter(AuthParams.USERNAME),
                req.getParameter(AuthParams.FULL_NAME),
                req.getParameter(AuthParams.PASSWORD),
                req.getParameter(AuthParams.CONFIRM_PASSWORD)
        );
    }

}
