<%-- 
    Document   : ViewTodo.jsp
    Created on : Dec 24, 2025, 5:11:56 PM
    Author     : Mouad OUMOUS
--%>



<%@page import="View.ViewResolver"%>
<%@page import="Data.Todo"%>
<%@ include file="<%= ViewResolver.resolve(ViewResolver.HEADER) %>"%>

<h3>View Todo</h3>

<%
    Todo todo = (Todo) request.getAttribute("todo");
%>
<div class="card">
    <div class="card-body">
        <h5 class="card-title"><%= todo.getTitle() %></h5>
        <p class="card-text"><%= todo.getDescription() %></p>

        <a href="${request.getContextPath()+"/todos"}" class="btn btn-secondary">Back</a>
    </div>
</div>
<%@ include file="<%= ViewResolver.resolve(ViewResolver.FOOTER) %>"%>
