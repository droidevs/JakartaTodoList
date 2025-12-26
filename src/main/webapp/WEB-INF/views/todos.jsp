<%-- 
    Document   : Todos
    Created on : Dec 24, 2025, 6:48:37 PM
    Author     : Mouad OUMOUS
--%>

<%@page import="View.ViewResolver"%>
<%@page import="Constants.TodoStatus"%>
<%@page import="Data.Todo"%>
<%@ page import="java.util.*" %>

<%@ include file="<%= ViewResolver.resolve(ViewResolver.HEADER) %>"%>


<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Todos List</h2>
    <a href="todo/create" class="btn btn-primary">New Todo</a>
</div>

<%
    List<Todo> todos = (List<Todo>) request.getAttribute("todos");
%>


<table class="table table-bordered table-hover align-middle">
    <thead class="table-dark">
    <tr>
        <th style="width: 25%">Title</th>
        <th>Description</th>
        <th style="width: 10%">Due Date</th>
        <th style="width: 10%">Status</th>
        <th style="width: 25%">Actions</th>
    </tr>
    </thead>

    <tbody>
    <%
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
        <td><%= todo.getDueDate() != null ? todo.getDueDate() : "-" %></td>
        <td>
            <span class="badge 
                    <%= todo.getStatus() == TodoStatus.NEW ? "bg-primary" :
                        todo.getStatus() == TodoStatus.IN_PROGRESS ? "bg-warning" :
                        todo.getStatus() == TodoStatus.COMPLETED ? "bg-success" :
                        "bg-danger" %>">
                    <%= todo.getStatus().name().replace("_", " ") %>
                </span>
        </td>
        
        <!-- Status update form -->
        <form action="todo/status" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= todo.getId() %>">
            <select name="status" class="form-select form-select-sm d-inline w-auto">
                <% for(TodoStatus s : TodoStatus.values()){ %>
                    <option value="<%= s.name() %>" <%= s == todo.getStatus() ? "selected" : "" %>><%= s.name().replace("_", " ") %></option>
                <% } %>
            </select>
            <button class="btn btn-sm btn-primary">Update</button>
        </form>
    
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

<%@ include file="<%= ViewResolver.resolve(ViewResolver.FOOTER) %>"%>

