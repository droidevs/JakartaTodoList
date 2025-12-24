<%-- 
    Document   : CreateTodo.jsp
    Created on : Dec 23, 2025, 5:04:01 PM
    Author     : admin
--%>

<%@page import="TodoServlet.Todo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create ToDo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Create a New ToDo</h2>
    <form action="${pageContext.request.contextPath}/todo/create" method="post">
        <% 
            Todo todo = (Todo) request.getAttribute("todo");
        %>
        <div class="mb-3">
            <%
                if (todo.getId() != 0) {
            %>
            <input type="hidden" class="form-control" id="id" name="id" value="<%= todo.getId() %>" required><!-- comment -->
            <% } %>
            <label for="title" class="form-label">Title:</label>
            <input type="text" class="form-control" id="title" name="title" value="<%= todo.getTitle() %>" required>
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Description:</label>
            <textarea class="form-control" id="description" name="description" rows="3" required>
                <%= todo.getDescription() %>
            </textarea>
        </div>
        <button type="submit" class="btn btn-primary">
            <% if (todo.isEmpty()) {
                   out.print("Create");
                } else {
                   out.print("Edit");
                }
            %> ToDo</button>
    </form>
</div>
</body>
</html>
