<%-- 
    Document   : signup
    Created on : Dec 25, 2025, 12:28:51 PM
    Author     : Mouad OUMOUS
--%>

<%@ page import="View.ComponentResolver" %>
<%@ page import="Paths.Paths" %>
<%@ page import="Paths.BasePaths" %>
<%@ page import="Paths.PathParams" %>
<%@ page import="Paths.Api" %>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.HEADER) %>" />


<h2>Create an Account</h2>
<form action="<%= request.getContextPath() + Paths.Auth.REGISTER() %>" method="post">
    <div class="mb-3">
        <label for="fullName">Full Name</label>
        <input id="fullName" type="text" name="fullName" class="form-control" required>
    </div>
    <div class="mb-3">
        <label for="username">Username</label>
        <input id="username" type="text" name="username" class="form-control" required>
    </div>
    <div class="mb-3">
        <label for="password">Password</label>
        <input id="password" type="password" name="password" class="form-control" required>
    </div>
    <div class="mb-3">
        <label for="confirmPassword">Confirm Password</label>
        <input id="confirmPassword" type="password" name="confirmPassword" class="form-control" required>
    </div>
    <input type="submit" class="btn btn-primary" value="Sign Up">
</form>

<% String error = (String) request.getAttribute("error");
   if(error != null){ %>
    <div class="alert alert-danger mt-3"><%= error %></div>
<% } %>


<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.FOOTER) %>" />
