<%-- 
    Document   : index.jsp
    Created on : Dec 25, 2025, 1:00:25 PM
    Author     : Mouad OUMOUS
--%>



<%@page import="View.ViewResolver"%>
<%@ include file="<%= ViewResolver.resolve(ViewResolver.HEADER) %>"%>


<h1 class="mb-4">Welcome to Todo App</h1>
<p class="lead mb-4">Organize your tasks efficiently and never miss a deadline!</p>

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

<%@ include file="<%= ViewResolver.resolve(ViewResolver.FOOTER) %>"%>

