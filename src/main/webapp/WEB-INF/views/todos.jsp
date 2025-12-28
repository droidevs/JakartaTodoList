<%-- 
    Document   : Todos
    Created on : Dec 24, 2025, 6:48:37 PM
    Author     : Mouad OUMOUS
--%>


<%@page import="View.ViewResolver"%>
<%@page import="Data.Todo"%>
<%@ page import="java.util.*" %>

<%@ include file="<%= ViewResolver.resolve(ViewResolver.HEADER) %>" %>

<style>
    .todo-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
        gap: 1.5rem;
        animation: fadeIn 0.6s ease;
    }

    .todo-card {
        background: #fff;
        border-radius: 16px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        position: relative;
        padding: 1.25rem;
        background: #fff;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .todo-card:hover {
        transform: translateY(-6px);
        box-shadow: 0 12px 25px rgba(0,0,0,0.12);
    }

    .todo-category {
        position: absolute;
        top: 14px;
        right: 14px;
        padding: 4px 10px;
        border-radius: 999px;
        color: #fff;
        font-size: 0.75rem;
        font-weight: 600;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }

    .todo-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 0.75rem;
    }

    .todo-title {
        font-weight: 600;
        margin: 0;
    }

    .todo-body {
        flex-grow: 1;
        margin-bottom: 1rem;
    }

    .todo-actions {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 0.5rem;
        margin-top: 1rem;
    }

    @keyframes fadeIn {
        from {
            opacity: 0;
            transform: translateY(10px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
</style>

<div class="container mt-4">

    <!-- HEADER -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">? Todos</h2>
        <a href="todo/create" class="btn btn-primary btn-lg">
            ? New Todo
        </a>
    </div>

    <%
        List<Todo> todos = (List<Todo>) request.getAttribute("todos");
    %>

    <% if (todos == null || todos.isEmpty()) { %>
    <div class="alert alert-info text-center">
        No todos yet. Create your first one ?
    </div>
    <% } else { %>

    <!-- GRID -->
    <div class="todo-grid">
        <% for (Todo todo : todos) { %>
        <%
            request.setAttribute("todo", todo);
        %>
        <jsp:include page="<%= ViewResolver.esolve(ViewResolver.TODO_ITEM) %>" />
        <% } %>
    </div>

    <% } %>

</div>

<%@ include file="<%= ViewResolver.esolve(ViewResolver.FOOTER) %>" %>
