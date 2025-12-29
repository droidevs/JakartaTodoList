
<%@page import="View.CssResolver"%>
<%@page import="View.ComponentResolver"%>
<%-- 
    Document   : todos
    Created on : Dec 29, 2025, 11:55:49 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page contentType="text/html;charset=UTF-8" %>

<%@page import="View.ViewResolver"%>
<%@page import="Data.Todo"%>
<%@ page import="java.util.*" %>

<jsp:include page="<%= ComponentResolver.HEADER %>">
    <jsp:param name="title" value="Todos List"/>
    <jsp:param name="css" value="<%= CssResolver.TODOS %>"/>
</jsp:include>


<div class="container mt-4"> 

    <!-- HEADER -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">? Todos</h2>
        <a href="todo/create" class="btn btn-primary btn-lg">
            ? New Todo
        </a>
    </div>

    <%
        List<Todo> todos = (List<Todo>) request.getAttribute("todos");
    %>

    <% if (todos == null || todos.isEmpty()) { %>
    <div class="alert alert-info text-center">
        No todos yet. Create your first one ?
    </div>
    <% } else { %>

    <!-- GRID -->
    <div class="todo-grid">
        <% for (Todo todo : todos) { %>
        <%
            request.setAttribute("todo", todo);
        %>
        <jsp:include page="<%= ComponentResolver.TODO_ITEM %>" />
        <% } %>
    </div>

    <% } %>

</div>

<%@ include file="<%= ComponentResolver.FOOTER %>" %>

