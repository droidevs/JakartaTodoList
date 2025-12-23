<%-- 
    Document   : Todos
    Created on : Dec 23, 2025, 4:14:54 PM
    Author     : admin
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Todo List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="p-4">
    <h1>My Todo List</h1>
    <form action="${pageContext.request.contextPath}/todos" method="post">
        <input type="text" name="task" class="form-control mb-2" placeholder="Add a new task"/>
        <button type="submit" class="btn btn-primary">Add</button>
    </form>
    <ul class="list-group mt-3">
        <p>${todos.size()}</p>
        <c:forEach items="${todos}" var="t">
            <li class="list-group-item">${t}</li>
        </c:forEach>
    </ul>
</body>
</html>

