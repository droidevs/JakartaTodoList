<%-- 
    Document   : header
    Created on : Dec 24, 2025, 4:49:56 PM
    Author     : Mouad OUMOUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%@ page session="true" %>

<%@ page import="java.util.*" %>

<%
    String pageTitle = request.getParameter("title");
    String pageCss = request.getParameter("css");
    
    if (pageTitle == null) {
        pageTitle = "Todo App";
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= pageTitle%></title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

        <link rel="stylesheet"
              href="<%= request.getContextPath() + pageCss%>">
        
        <style>

            

            

        </style>
    </head>
    <body>

        <%
            String username = (String) session.getAttribute("user");
        %>    
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
            <div class="container">
                <a class="navbar-brand" href="todos">Todo App</a>

                <div class="collapse navbar-collapse justify-content-end">
                    <ul class="navbar-nav">
                        <% if (username != null) {%>
                        <li class="nav-item me-3 bi bi-person-circle">
                            <span class="navbar-text text-light">
                                <i class="bi bi-person-circle"></i> <%= username%>
                            </span>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm" href="logout" 
                               onclick="return confirm('Are you sure you want to logout?');">
                                Logout
                            </a>
                        </li>
                        <% } else { %>
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm" href="login.jsp">Login</a>
                        </li>
                        <% }%>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">
