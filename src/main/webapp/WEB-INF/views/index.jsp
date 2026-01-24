<%-- 
    Document   : index.jsp
    Created on : Dec 25, 2025, 1:00:25 PM
    Author     : Mouad OUMOUS
--%>



<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="View.ComponentResolver" %>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.HEADER) %>" />

<div class="container mt-4">
    <h1>Welcome to Todo App</h1>
</div>

<hr>

<div class="mt-5">
    <h3>About the Developer</h3>
    <p>Mouad - Android & Web Developer. Passionate about building clean, functional apps.</p>
    <p>Contact: <a href="mailto:developer@example.com">developer@example.com</a></p>
</div>

<div class="mt-5">
    <h3>Features</h3>
    <ul class="list-group list-group-flush">
        <li class="list-group-item">Add, edit, and delete todos</li>
        <li class="list-group-item">User authentication with sessions</li>
        <li class="list-group-item">Responsive and modern UI with Bootstrap</li>
    </ul>
</div>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.FOOTER) %>" />
