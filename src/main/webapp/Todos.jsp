<%-- 
    Document   : Todos
    Created on : Dec 24, 2025, 6:48:37 PM
    Author     : admin
--%>

<%@ page import="java.util.*" %>
<%@ page import="TodoServlet.Todo" %>

<%@ include file="/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Todos List</h2>
    <a href="todo/create" class="btn btn-primary">New Todo</a>
</div>

<table class="table table-bordered table-hover align-middle">
    <thead class="table-dark">
    <tr>
        <th style="width: 25%">Title</th>
        <th>Description</th>
        <th style="width: 25%">Actions</th>
    </tr>
    </thead>

    <tbody>
    <%
        Collection<Todo> todos =
                (Collection<Todo>) request.getAttribute("todos");

        if (todos == null || todos.isEmpty()) {
    %>
    <tr>
        <td colspan="3" class="text-center text-muted">
            No todos found.
        </td>
    </tr>
    <%
        } else {
            for (Todo todo : todos) {
    %>
    <tr>
        <td><%= todo.getTitle() %></td>
        <td><%= todo.getDescription() %></td>

        <td class="text-nowrap">

            <!-- VIEW -->
            <form action="todo/view" method="get" style="display:inline;">
                <input type="hidden" name="id" value="<%= todo.getId() %>">
                <button class="btn btn-info btn-sm">View</button>
            </form>

            <!-- EDIT -->
            <form action="todo/create" method="get" style="display:inline;">
                <input type="hidden" name="id" value="<%= todo.getId() %>">
                <button class="btn btn-secondary btn-sm">Edit</button>
            </form>

            <!-- DELETE -->
            <form action="todo/delete" method="get"
                  style="display:inline;"
                  onsubmit="return confirm('Delete this todo?');">
                <input type="hidden" name="id" value="<%= todo.getId() %>">
                <button class="btn btn-danger btn-sm">Delete</button>
            </form>

        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<%@ include file="/footer.jsp" %>
