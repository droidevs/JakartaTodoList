<%-- 
    Document   : CreateTodo.jsp
    Created on : Dec 23, 2025, 5:04:01 PM
    Author     : Mouad OUMOUS
--%>

<%@page import="TodoServlet.Todo"%>

<%@ include file="header.jsp" %>

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

<%@ include file="footer.jsp" %>