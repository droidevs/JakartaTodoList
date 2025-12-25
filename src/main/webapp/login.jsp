<%-- 
    Document   : Login
    Created on : Dec 25, 2025, 10:22:52 AM
    Author     : admin
--%>


<%@ include file="header.jsp"%>

<h2>Login</h2>
<form action="login" method="post">
    <div class="mb-3">
        <label>Username</label>
        <input type="text" name="username" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Password</label>
        <input type="password" name="password" class="form-control" required>
    </div>
    <input type="submit" class="btn btn-primary" value="Login">
</form>
<% String error = (String) request.getAttribute("error");
   if(error != null){ %>
    <div class="alert alert-danger mt-3"><%= error %></div>
<% } %>

<%@ include file="footer.jsp"%>
