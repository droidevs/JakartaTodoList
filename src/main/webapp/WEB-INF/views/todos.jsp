<%@page import="View.CssResolver"%>
<%@page import="View.ComponentResolver"%>
<%@ page import="Paths.Paths" %>
<%@ page import="Data.Todo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
    Document   : todos
    Created on : Dec 29, 2025, 11:55:49 AM
    Author     : admin
--%>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.HEADER) %>">
    <jsp:param name="title" value="Todos List"/>
    <jsp:param name="css" value="<%= CssResolver.TODOS %>"/>
</jsp:include>


<div class="container mt-4"> 

    <!-- HEADER -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">? Todos</h2>
        <a href="<%= request.getContextPath() + Paths.Todos.CREATE_FORM() %>" class="btn btn-primary btn-lg">
            ? New Todo
        </a>
    </div>

    <%
        Object todosObj = request.getAttribute("todos");
        java.util.List<Todo> todos = (todosObj instanceof java.util.List) ? (java.util.List<Todo>) todosObj : java.util.Collections.emptyList();
    %>

    <% if (todos == null || todos.isEmpty()) { %>
    <div class="alert alert-info text-center">
        No todos yet. Create your first one ?
    </div>
    <% } else { %>

    <!-- GRID -->
    <div class="todo-grid row g-3">
        <% for (Todo todo : todos) { %>
        <div class="col-lg-6">
            <%
                request.setAttribute("todo", todo);
                request.setAttribute("suppressCategory", false);
            %>
            <jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.TODO_ITEM) %>" />
        </div>
        <% } %>
    </div>

    <% } %>

</div>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.FOOTER) %>" />
