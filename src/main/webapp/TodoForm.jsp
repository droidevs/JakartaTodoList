<%-- 
    Document   : CreateTodo.jsp
    Created on : Dec 23, 2025, 5:04:01 PM
    Author     : Mouad OUMOUS
--%>

<%@page import="Constants.TodoStatus"%>
<%@page import="Data.Todo"%>

<%@ include file="header.jsp" %>

<h2>Create a New ToDo</h2>
<form action="${pageContext.request.contextPath}/todo/create" method="post">
    <%
        Todo todo = (Todo) request.getAttribute("todo");
    %>
    <div class="mb-3">
        <%
            if (todo.getId() != -1) {
        %>
        <input type="hidden" class="form-control" id="id" name="id" value="<%= todo.getId()%>" required><!-- comment -->
        <% }%>
        <label for="title" class="form-label">Title:</label>
        <input type="text" class="form-control" id="title" name="title" value="<%= todo.getTitle()%>" required>
    </div>
    <div class="mb-3">
        <label for="description" class="form-label">Description:</label>
        <textarea class="form-control" id="description" name="description" rows="3" required>
            <%= todo.getDescription()%>
        </textarea>
    </div>
    <div class="mb-3">
        <label for="description" class="form-label">Description:</label>
        <input type="date"
               name="dueDate"
               class="form-control"
               value="<%= todo.getDueDate()%>"
               <%= todo.getStatus() == TodoStatus.OVERDUE ? "disabled" : ""%>>
    </div>
    <div class="mb-3">
        <label>Status</label>
        <select name="status" class="form-select">
            <%
                TodoStatus current = todo.getStatus();
                for (TodoStatus s : TodoStatus.values()) {

                    boolean disabled = false;

                    if (current == TodoStatus.COMPLETED && s != TodoStatus.COMPLETED) {
                        disabled = true;
                    }

                    if (current != TodoStatus.NEW && s == TodoStatus.NEW) {
                        disabled = true;
                    }

                    if (current == TodoStatus.OVERDUE
                            && (s == TodoStatus.NEW || s == TodoStatus.IN_PROGRESS)) {
                        disabled = true;
                    }
            %>
            <option value="<%= s.name()%>"
                    <%= s == current ? "selected" : ""%>
                    <%= disabled ? "disabled" : ""%>>
                <%= s.name().replace("_", " ")%>
            </option>
            <% } %>
        </select>

    </div>
    <button type="submit" class="btn btn-primary">
        <% if (todo.isEmpty()) {
                out.print("Create");
            } else {
                out.print("Edit");
            }
        %> ToDo</button>
</form>

<% if (todo.getStatus() == TodoStatus.OVERDUE) { %>
    <div class="alert alert-warning">
        Overdue todos can only be completed after 3 days.
    </div>
<% } %>

<%@ include file="footer.jsp" %>