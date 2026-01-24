<%-- 
    Document   : todo_form
    Created on : Dec 29, 2025, 12:02:25 PM
    Author     : admin
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="View.ComponentResolver"%>

<%@page import="java.util.List"%>
<%@page import="Constants.TodoStatus"%>
<%@page import="Data.Todo"%>
<%@page import="Data.Category"%>
<%@page import="Paths.Api"%>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.HEADER)%>" />

<%
    Todo todo = (Todo) request.getAttribute("todo");
    List<Category> categories = (List<Category>) request.getAttribute("categories");

    // Defensive null handling: when creating a new ttodo the 'ttodo' attribute may be null
    boolean isEdit = (todo != null && todo.getId() != 0);

    // Provide safe defaults for status and category where necessary
    TodoStatus currentStatus = (todo != null && todo.getStatus() != null) ? todo.getStatus() : TodoStatus.NEW;

    String actionUrl = isEdit
            ? request.getContextPath() + Api.TODOS_UPDATE(todo.getId()).getPath()
            : request.getContextPath() + Api.TODOS_CREATE.getPath();

    // Ensure categories is not null to avoid loops failing
    if (categories == null) {
        categories = java.util.Collections.emptyList();
    }
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
                           value="<%= (todo != null && todo.getTitle() != null) ? todo.getTitle() : "" %>"
                           required>
                </div>

                <!-- DESCRIPTION -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Description</label>
                    <textarea name="description"
                              class="form-control"
                              rows="4"
                              required><%= (todo != null && todo.getDescription() != null) ? todo.getDescription() : "" %></textarea>
                </div>

                <!-- DUE DATE -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Due Date</label>
                    <input type="date"
                           name="dueDate"
                           class="form-control"
                           value="<%= (todo != null && todo.getDueDate() != null) ? todo.getDueDate().toString() : "" %>"
                           min="<%= java.time.LocalDate.now().toString()%>"
                           <%= (todo != null && todo.getStatus() == TodoStatus.OVERDUE) ? "disabled" : ""%> >
                </div>

                <!-- CATEGORY -->
                <%--
                    Removed category select per request. Category selection is now optional.
                    If a categoryId was provided via query param (e.g., from category view) or the todo
                    already has a category (edit mode), we preserve it using a hidden input so the
                    backend can associate the todo with that category. No category is required.
                --%>
                <% if (isEdit) { %>
                <!-- In edit mode allow changing category: optional select with empty option for 'no category' -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Category</label>
                    <select name="categoryId" class="form-select">
                        <option value="" <%= (todo.getCategory() == null) ? "selected" : "" %>>-- No category --</option>
                        <% for (Category c : categories) { %>
                        <option value="<%= c.getId()%>"
                                <%= (todo != null && todo.getCategory() != null && c.getId() != null && c.getId().equals(todo.getCategory().getId())) ? "selected" : ""%> >
                            <%= c.getName()%>
                        </option>
                        <% } %>
                    </select>
                </div>
                <% } else {
                    // create mode: if categoryId query param was provided, keep it hidden; otherwise show select so user can choose
                    String providedCategoryId = (String) request.getAttribute("categoryId");
                    if (providedCategoryId != null && !providedCategoryId.isBlank()) {
                %>
                <input type="hidden" name="categoryId" value="<%= providedCategoryId %>">
                <%      } else { %>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Category (optional)</label>
                    <select name="categoryId" class="form-select">
                        <option value="" <%= (todo == null || todo.getCategory() == null) ? "selected" : "" %>>-- No category --</option>
                        <% for (Category c : categories) { %>
                        <option value="<%= c.getId()%>"
                                <%= (todo != null && todo.getCategory() != null && c.getId() != null && c.getId().equals(todo.getCategory().getId())) ? "selected" : ""%> >
                            <%= c.getName()%>
                        </option>
                        <% } %>
                    </select>
                </div>
                <%      }
                } %>

                <!-- STATUS -->
                <div class="mb-4">
                    <label class="form-label fw-semibold">Status</label>
                    <select name="status" class="form-select">

                        <%
                            for (TodoStatus s : TodoStatus.values()) {

                                boolean disabled = false;

                                if (currentStatus == TodoStatus.COMPLETED && s != TodoStatus.COMPLETED) {
                                    disabled = true;
                                }

                                if (currentStatus != TodoStatus.NEW && s == TodoStatus.NEW) {
                                    disabled = true;
                                }

                                if (currentStatus == TodoStatus.OVERDUE
                                        && (s == TodoStatus.NEW || s == TodoStatus.IN_PROGRESS))
                                    disabled = true;
                        %>

                        <option value="<%= s.name()%>"
                                <%= s == currentStatus ? "selected" : ""%>
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

            <% if (todo != null && todo.getStatus() == TodoStatus.OVERDUE) { %>
            <div class="alert alert-warning mt-4">
                ⚠ Overdue todos can only be completed after 3 days.
            </div>
            <% }%>

        </div>
    </div>
</div>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.FOOTER)%>" />
