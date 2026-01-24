<%--
    Document   : categories
    Created on : Dec 29, 2025, 11:21:45 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="View.ComponentResolver"%>
<%@page import="View.CssResolver"%>
<%@page import="Paths.Api"%>

<%@page import="java.util.List"%>
<%@page import="Data.Category"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Objects"%>
<%@ page import="Paths.Paths" %>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.HEADER) %>">
    <jsp:param name="title" value="Categories list" />
    <jsp:param name="css" value= "<%= CssResolver.CATEGORIES %>" />
</jsp:include>

<div class="container mt-4">

    <!-- HEADER -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">📂 Categories</h2>

        <a href="<%= request.getContextPath() + Api.CATEGORIES_CREATE.getPath() %>"
           class="btn btn-primary shadow-sm">
            ➕ New Category
        </a>
    </div>

    <%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        Map<Integer,Integer> todoCounts = (Map<Integer,Integer>) request.getAttribute("todoCounts");
        if (todoCounts == null) todoCounts = java.util.Collections.emptyMap();
    %>

    <!-- EMPTY STATE -->
    <% if (categories == null || categories.isEmpty()) { %>
    <div class="text-center text-muted py-5">
        <h5>No categories yet</h5>
        <p>Create your first category to organize todos.</p>
    </div>
    <% } else { %>

    <!-- GRID -->
    <div class="row g-4 category-grid">

        <% for (Category category : categories) { %>

        <div class="col-xl-3 col-lg-4 col-md-6 category-animate">
            <a href="<%= request.getContextPath() + Api.CATEGORIES_GET_ONE(category.getId()).getPath() %>"
               class="text-decoration-none">

                <div class="category-card shadow-sm h-100">

                    <!-- COLOR BAR -->
                    <div class="category-color"
                         style="background-color:<%= category.getColor() %>"></div>

                    <!-- CONTENT -->
                    <div class="p-3">
                        <h5 class="fw-bold text-dark mb-1">
                            <%= category.getName() %>
                        </h5>

                        <div class="d-flex justify-content-between align-items-center">
                            <div class="text-muted small">
                                <%= Objects.toString(todoCounts.getOrDefault(category.getId(), 0)) %> Todos
                            </div>
                            <div>
                                <a href="<%= request.getContextPath() + Paths.Todos.CREATE() %>?categoryId=<%= category.getId()%>" class="btn btn-sm btn-outline-primary">+ Create Todo</a>
                            </div>
                        </div>
                    </div>

                </div>
            </a>
        </div>

        <% } %>

    </div>
    <% } %>

</div>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.FOOTER) %>" />
