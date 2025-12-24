<%-- 
    Document   : Todos
    Created on : Dec 24, 2025, 6:48:37 PM
    Author     : admin
--%>

<%@ page import="java.util.*, TodoServlet.Todo" %>
<%@ include file="header.jsp" %>

<h2 class="mb-4">Todo List</h2>

<!-- Optional: Add New Todo button -->
<div class="mb-3">
    <a href="todo/create" class="btn btn-primary">Add New Todo</a>
</div>

<table class="table table-bordered table-hover align-middle">
    <thead class="table-dark">
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th style="width: 220px;">Actions</th>
        </tr>
    </thead>
    <tbody>
        <%
            Collection<Todo> todos = (Collection<Todo>) request.getAttribute("todos");
            if (todos != null && !todos.isEmpty()) {
                for (Todo todo : todos) {
        %>
        <tr>
            <!-- Optional: Row click to View -->
            <td>
                <form action="todo/view" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                    <input type="submit" class="btn btn-link p-0" style="text-decoration:none;" value="<%= todo.getTitle() %>">
                </form>
            </td>
            <td><%= todo.getDescription() %></td>
            <td>
                <!-- View button -->
                <form action="todo/view" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                    <input type="submit" class="btn btn-info btn-sm" value="View">
                </form>

                <!-- Edit button -->
                <form action="todo/create" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                    <input type="submit" class="btn btn-secondary btn-sm" value="Edit">
                </form>

                <!-- Delete button -->
                <a href="todo/delete?id=<%= todo.getId() %>" 
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Delete this todo?');">
                    Delete
                </a>
            </td>
        </tr>
        <%      }
            } else { %>
        <tr>
            <td colspan="3" class="text-center">No todos found</td>
        </tr>
        <% } %>
    </tbody>
</table>

<!-- Optional: Debugging: print to console -->
<%
    if (todos != null) {
        for (Todo todo : todos) {
            System.out.println("Todo loaded: ID=" + todo.getId() + ", Title=" + todo.getTitle());
        }
    }
%>

<%@ include file="footer.jsp" %>

<!-- Optional: Hover effect for rows -->
<style>
    table tbody tr:hover {
        background-color: #f2f2f2;
    }
</style>

