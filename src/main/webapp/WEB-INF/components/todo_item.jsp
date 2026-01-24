<%-- 
    Document   : todo_item.jsp
    Created on : Dec 28, 2025, 7:25:05 PM
    Author     : admin
--%>

<%@page import="Constants.TodoStatus"%>
<%@page import="Data.Todo"%>
<%@page import="Data.Category"%>
<%@page import="Constants.Defaults"%>
<%@page import="Paths.Api"%>

<%
    Todo todo = (Todo) request.getAttribute("todo");
    Category category = todo.getCategory();

    String categoryName  = category != null ? category.getName() : "Uncategorized";
    // Use a central default color when todo has no category
    String categoryColor = category != null ? category.getColor() : Defaults.NO_CATEGORY_COLOR;
    // allow caller to suppress showing the category (e.g., when rendering inside a category view)
    Boolean suppressCategory = (Boolean) request.getAttribute("suppressCategory");
    if (suppressCategory == null) suppressCategory = false;
%>

<div class="todo-card shadow-sm mb-3"
     style="border-left: 6px solid <%= categoryColor %>; border-radius:12px; overflow:hidden">
    <!-- CATEGORY TAG (EDGE) -->
    <% if (!suppressCategory) { %>
    <div class="todo-category text-white px-2 py-1"
         style="background-color: <%= categoryColor %>; position:absolute; top:12px; right:12px; border-radius:8px;">
        <%= categoryName %>
    </div>
    <% } %>
    <!-- HEADER -->
    <div class="todo-header">
        <h5 class="todo-title"><%= todo.getTitle() %></h5>
        <span class="badge
            <%= todo.getStatus() == TodoStatus.NEW ? "bg-primary" :
                todo.getStatus() == TodoStatus.IN_PROGRESS ? "bg-warning" :
                todo.getStatus() == TodoStatus.COMPLETED ? "bg-success" :
                "bg-danger" %>">
            <%= todo.getStatus().name().replace("_", " ") %>
        </span>
    </div>
    <!-- BODY -->
    <div class="todo-body">
        <p class="text-muted mb-2"><%= todo.getDescription() %></p>

        <div class="small text-secondary">
            ? Due:
            <strong>
                <%= todo.getDueDate() != null ? todo.getDueDate() : "No date" %>
            </strong>
        </div>
    </div>
    <!-- ACTIONS -->
    <div class="todo-actions d-flex gap-2 mt-3">
        <form action="<%= request.getContextPath() + Api.TODOS_GET_ONE(todo.getId()).getPath() %>" method="get">
            <input type="hidden" name="id" value="<%= todo.getId() %>">
            <button class="btn btn-outline-info btn-sm">View</button>
        </form>

        <form action="<%= request.getContextPath() + Api.TODOS_UPDATE_FORM(todo.getId()).getPath() %>" method="get">
            <input type="hidden" name="id" value="<%= todo.getId() %>">
            <button class="btn btn-outline-secondary btn-sm">Edit</button>
        </form>

        <form action="<%= request.getContextPath() + Api.TODOS_DELETE(todo.getId()).getPath() %>" method="post"
              onsubmit="return confirm('Delete this todo?');">
            <input type="hidden" name="id" value="<%= todo.getId() %>">
            <button class="btn btn-outline-danger btn-sm">Delete</button>
        </form>
    </div>
</div>
