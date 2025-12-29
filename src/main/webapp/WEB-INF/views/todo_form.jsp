<%-- 
    Document   : todo_form
    Created on : Dec 29, 2025, 12:02:25 PM
    Author     : admin
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="View.ComponentResolver"%>

<%@page import="java.util.List"%>
<%@page import="View.ViewResolver"%>
<%@page import="Constants.TodoStatus"%>
<%@page import="Data.Todo"%>
<%@page import="Data.Category"%>
<%@page import="Paths.Api"%>

<%@ include file="<%= ComponentResolver.HEADER%>" %>

<%
    Todo todo = (Todo) request.getAttribute("todo");
    List<Category> categories = (List<Category>) request.getAttribute("categories");

    boolean isEdit = !todo.isEmpty();

    String actionUrl = isEdit
            ? request.getContextPath() + Api.TODOS_UPDATE(todo.getId())
            : request.getContextPath() + Api.TODOS_CREATE;
%>

<style>
    .todo-card {
        max-width: 720px;
        margin: auto;
        animation: fadeSlide 0.6s ease;
        border-radius: 16px;
    }

    @keyframes fadeSlide {
        from {
            opacity: 0;
            transform: translateY(20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    .form-control:focus,
    .form-select:focus {
        box-shadow: 0 0 0 0.2rem rgba(13,110,253,.25);
    }
</style>

<div class="container mt-5">
    <div class="card shadow-lg todo-card">
        <div class="card-body p-5">

            <h3 class="mb-4 text-center fw-bold">
                <%= isEdit ? "✏️ Edit Todo" : "➕ Create New Todo"%>
            </h3>

            <form method="post" action="<%= actionUrl%>">

                <% if (isEdit) {%>
                <input type="hidden" name="id" value="<%= todo.getId()%>">
                <% }%>

                <!-- TITLE -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Title</label>
                    <input type="text"
                           name="title"
                           class="form-control form-control-lg"
                           value="<%= todo.getTitle()%>"
                           required>
                </div>

                <!-- DESCRIPTION -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Description</label>
                    <textarea name="description"
                              class="form-control"
                              rows="4"
                              required><%= todo.getDescription()%></textarea>
                </div>

                <!-- DUE DATE -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Due Date</label>
                    <input type="date"
                           name="dueDate"
                           class="form-control"
                           value="<%= todo.getDueDate() != null ? todo.getDueDate().toString() : ""%>"
                           min="<%= java.time.LocalDate.now().toString()%>"
                           <%= todo.getStatus() == TodoStatus.OVERDUE ? "disabled" : ""%> >
                </div>

                <!-- CATEGORY -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Category</label>
                    <select name="categoryId" class="form-select" required>
                        <option value="">-- Select category --</option>
                        <% for (Category c : categories) {%>
                        <option value="<%= c.getId()%>"
                                <%= c.getId() == todo.getCategory().getId() ? "selected" : ""%> >
                            <%= c.getName()%>
                        </option>
                        <% } %>
                    </select>
                </div>

                <!-- STATUS -->
                <div class="mb-4">
                    <label class="form-label fw-semibold">Status</label>
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
                                        && (s == TodoStatus.NEW || s == TodoStatus.IN_PROGRESS))
                                    disabled = true;
                        %>

                        <option value="<%= s.name()%>"
                                <%= s == current ? "selected" : ""%>
                                <%= disabled ? "disabled" : ""%>>
                            <%= s.name().replace("_", " ")%>
                        </option>

                        <% }%>
                    </select>
                </div>

                <!-- ACTION BUTTON -->
                <div class="d-grid">
                    <button type="submit"
                            class="btn btn-primary btn-lg fw-bold">
                        <%= isEdit ? "Update Todo" : "Create Todo"%>
                    </button>
                </div>

            </form>

            <% if (todo.getStatus() == TodoStatus.OVERDUE) { %>
            <div class="alert alert-warning mt-4">
                ⚠ Overdue todos can only be completed after 3 days.
            </div>
            <% }%>

        </div>
    </div>
</div>

<%@ include file="<%= ComponentResolver.FOOTER%>" %>
