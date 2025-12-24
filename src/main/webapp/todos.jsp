<%-- 
    Document   : Todos
    Created on : Dec 23, 2025, 4:14:54 PM
    Author     : admin
--%>
<%@page import="java.util.Collection"%>
<%@page import="TodoServlet.Todo"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Todo List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="p-4">
    <div class="container mt-5">
    <h2>Todos List</h2>
    <a href="${pageContext.request.contextPath}/todo/create" class="btn btn-success mb-3">Add New Todo</a>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            Collection<Todo> todos = (Collection<Todo>) request.getAttribute("todos");
            if (todos != null) {
                for (Todo todo : todos) {
        %>
        <tr>
            <td><%= todo.getTitle() %></td>
            <td><%= todo.getDescription() %></td>
            <td>
                <form action="todo/create" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%=todo.getId()%>">
                    <input class="btn btn-secondary btn-sm" type="submit" value="Edit">
                </form>
                <form action="todo/delete" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                    <input class="btn btn-danger btn-sm" type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <%      }
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>

