<%-- 
    Document   : error
    Created on : Dec 26, 2025, 9:28:39 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { font-family: Arial, sans-serif; background: #f8f9fa; }

        /* Standard Error Box */
        .container { margin-top: 80px; max-width: 700px; }
        .box { padding: 30px; border-radius: 10px; background: white; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }

        /* 404 specific */
        .container-404 { display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100vh; text-align: center; }
        .container-404 h1 { font-size: 10rem; color: #dc3545; animation: bounce 2s infinite; }
        .container-404 h3 { font-size: 2rem; color: #495057; margin-bottom: 20px; }
        .container-404 p { font-size: 1.2rem; color: #6c757d; }
        .btn-home { margin-top: 20px; animation: fadeInUp 1s ease-in-out; }

        @keyframes bounce {
            0%, 20%, 50%, 80%, 100% { transform: translateY(0); }
            40% { transform: translateY(-20px); }
            60% { transform: translateY(-10px); }
        }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<c:choose>
    <!-- ===== 404 PAGE ===== -->
    <c:when test="${statusCode == 404}">
        <div class="container-404">
            <h1>404</h1>
            <h3>Oops! Page not found</h3>
            <p>The page you are looking for might have been removed or never existed.</p>
            <a href="/" class="btn btn-primary btn-lg btn-home">Go Back Home</a>
        </div>
    </c:when>

    <!-- ===== STANDARD ERROR PAGE ===== -->
    <c:otherwise>
        <div class="container box">
            <h2 class="text-danger mb-4">Oops! Something went wrong</h2>

            <p><strong>Status Code:</strong> <c:out value="${statusCode}" /></p>
            <p><strong>Request URI:</strong> <c:out value="${requestUri}" /></p>
            <p><strong>Servlet Name:</strong> <c:out value="${servletName}" /></p>

            <c:if test="${not empty exception}">
                <p><strong>Exception:</strong> <c:out value="${exception.message}" /></p>
            </c:if>

            <hr>
            <div class="text-center mt-4">
                <a href="todos" class="btn btn-primary">Go to Todos</a>
                <a href="/" class="btn btn-secondary">Go Home</a>
            </div>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>

