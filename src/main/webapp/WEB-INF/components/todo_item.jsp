<%-- 
    Document   : todo_item.jsp
    Created on : Dec 28, 2025, 7:25:05 PM
    Author     : admin
--%>

<%@page import="Constants.TodoStatus"%>
<%@page import="Data.Todo"%>
<%@page import="Data.Category"%>

<%
    Todo todo = (Todo) request.getAttribute("todo");
    Category category = todo.getCategory();

    String categoryName  = category != null ? category.getName() : "Uncategorized";
    String categoryColor = category != null ? category.getColor() : "#6c757d"; // bootstrap secondary
%>

<div class="todo-card shadow-sm"
     style="border-left: 6px solid <%= categoryColor %>">

    <!-- CATEGORY TAG (EDGE) -->
    <div class="todo-category"
         style="background-color: <%= categoryColor %>">
        <%= categoryName %>
    </div>

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
    <div class="todo-actions">

        <form action="todo/view" method="get">
            <input type="hidden" name="id" value="<%= todo.getId() %>">
            <button class="btn btn-outline-info btn-sm w-100">View</button>
        </form>

        <form action="todo/create" method="get">
            <input type="hidden" name="id" value="<%= todo.getId() %>">
            <button class="btn btn-outline-secondary btn-sm w-100">Edit</button>
        </form>

        <form action="todo/delete" method="get"
              onsubmit="return confirm('Delete this todo?');">
            <input type="hidden" name="id" value="<%= todo.getId() %>">
            <button class="btn btn-outline-danger btn-sm w-100">Delete</button>
        </form>

    </div>
</div>


