<%-- 
    Document   : signup
    Created on : Dec 25, 2025, 12:28:51 PM
    Author     : Mouad OUMOUS
--%>

<%@ include file="header.jsp"%>

<h2>Create an Account</h2>
<form action="signup" method="post">
    <div class="mb-3">
        <label>Full Name</label>
        <input type="text" name="fullName" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Username</label>
        <input type="text" name="username" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Password</label>
        <input type="password" name="password" class="form-control" required>
    </div>
    <input type="submit" class="btn btn-primary" value="Sign Up">
</form>

<% String error = (String) request.getAttribute("error");
   if(error != null){ %>
    <div class="alert alert-danger mt-3"><%= error %></div>
<% } %>


<%@ include file="footer.jsp"%>