<%-- 
    Document   : category_view
    Created on : Dec 29, 2025, 10:28:38 AM
    Author     : admin
--%>

<%@page import="View.CssResolver"%>
<%@page import="View.ComponentResolver"%>
<%@page import="Paths.Api"%>
<%@ page import="Paths.Paths" %>
<%@ page import="java.util.List" %>
<%@ page import="Data.Category" %>
<%@ page import="Data.Todo" %>

<%
    Category category = (Category) request.getAttribute("category");
    Object todosObj = request.getAttribute("todos");
    java.util.List<Todo> todos = (todosObj instanceof java.util.List) ? (java.util.List<Todo>) todosObj : java.util.Collections.emptyList();
%>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.HEADER) %>">
    <jsp:param name="title" value="View Category"/>
    <jsp:param name="css" value="<%= CssResolver.CATEGORY_VIEW %>"/>
</jsp:include>

<!-- CATEGORY HEADER -->
<div class="category-header shadow position-relative">
    <div class="d-flex justify-content-between align-items-center">

        <!-- LEFT : TITLE -->
        <div>
            <div class="category-title fw-bold fs-4">
                <%= category.getName()%>
            </div>
            <div class="opacity-75 small">
                <%= todos.size()%> Todos
            </div>
        </div>

        <!-- RIGHT : ACTION ICONS -->
        <div class="d-flex align-items-center gap-2">

            <!-- EDIT -->
            <a href="<%= request.getContextPath() + Api.CATEGORY_EDIT_FORM(category.getId()).getPath()%>"
               class="btn btn-outline-secondary btn-sm rounded-circle"
               title="Edit category">
                <i class="bi bi-pencil"></i>
            </a>

            <!-- DELETE -->
            <form action="<%= request.getContextPath() + Api.CATEGORIES_DELETE(category.getId())%>"
                  method="post"
                  onsubmit="return confirm('Delete this category and all its todos?');">

                <button type="submit"
                        class="btn btn-outline-danger btn-sm rounded-circle"
                        title="Delete category">
                    <i class="bi bi-trash"></i>
                </button>
            </form>

        </div>
    </div>
</div>


<!-- TODOS -->
<%
    if (todos.isEmpty()) {
%>
<div class="empty-state">
    <h4>No todos in this category</h4>
    <p>Add a new todo to get started</p>

    <a href="<%= request.getContextPath() + Paths.Todos.CREATE() %>?categoryId=<%= category.getId()%>"
       class="btn btn-primary mt-2">
        + Create Todo
    </a>
</div>
<%
} else {
%>
<div class="todos-grid">
    <% for (Todo todo : todos) { %>
    <div class="todo-wrapper">
        <%
            request.setAttribute("todo", todo);
            request.setAttribute("suppressCategory", true);
        %>
        <jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.TODO_ITEM) %>"/>
    </div>
    <% } %>
</div>
<%
    }
%>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.FOOTER) %>" />
