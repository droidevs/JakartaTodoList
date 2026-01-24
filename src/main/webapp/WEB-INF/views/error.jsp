<%-- 
    Document   : error
    Created on : Dec 26, 2025, 9:28:39 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // Status code (from error page forwarding)
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
        statusCode = 200; // default OK
    }

    // Main message
    String message = (String) request.getAttribute("message");
    if (message == null) {
        message = "";
    }

    // Redirect URL
    String redirectUrl = (String) request.getAttribute("redirectUrl");
    if (redirectUrl == null) {
        redirectUrl = "";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Error</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f8f9fa;
                margin-top: 60px;
            }
            .response-box {
                max-width: 700px;
                margin: auto;
                padding: 30px;
                border-radius: 12px;
                background: #fff;
                box-shadow: 0 4px 15px rgba(0,0,0,0.15);
            }
            .success-title {
                color: #198754;
            }
            .error-title {
                color: #dc3545;
            }
            /* Standard Error Box */
            .container {
                margin-top: 80px;
                max-width: 700px;
            }
            .box {
                padding: 30px;
                border-radius: 10px;
                background: white;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }

            /* 404 specific */
            .container-404 {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                height: 100vh;
                text-align: center;
            }
            .container-404 h1 {
                font-size: 10rem;
                color: #dc3545;
                animation: bounce 2s infinite;
            }
            .container-404 h3 {
                font-size: 2rem;
                color: #495057;
                margin-bottom: 20px;
            }
            .container-404 p {
                font-size: 1.2rem;
                color: #6c757d;
            }
            .btn-home {
                margin-top: 20px;
                animation: fadeInUp 1s ease-in-out;
            }

            @keyframes bounce {
                0%, 20%, 50%, 80%, 100% {
                    transform: translateY(0);
                }
                40% {
                    transform: translateY(-20px);
                }
                60% {
                    transform: translateY(-10px);
                }
            }

            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
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
                    <a href="<%= request.getContextPath() %>/" class="btn btn-primary btn-lg btn-home">Go Back Home</a>
                </div>
            </c:when>

            <!-- ===== STANDARD ERROR PAGE ===== -->
            <c:otherwise>
                <div class="response-box">

                    <!-- Title -->
                    <h2 class="text-center mb-4">
                        <span class="error-title">❌ Error</span>
                    </h2>

                    <!-- Main Message -->
                    <c:if test="${not empty message}">
                        <p class="text-center fs-5">${message}</p>
                    </c:if>
                        
                    <!-- Redirect Countdown -->
                    <c:if test="${not empty redirectUrl}">
                        <div class="alert alert-info text-center mt-4">
                            You will be redirected in <strong><span id="countdown">3</span></strong> seconds...
                        </div>
                    </c:if>

                    <!-- Action Buttons -->
                    <div class="text-center mt-4">
                        <c:if test="${not empty redirectUrl}">
                            <a href="${redirectUrl}" class="btn btn-primary me-2">Go Now</a>
                        </c:if>
                        <a href="javascript:history.back()" class="btn btn-outline-secondary">← Go Back</a>
                    </div>

                </div>

                <!-- Redirect Script -->
                <c:if test="${not empty redirectUrl}">
                    <script>
                        let seconds = 3;
                        const countdown = document.getElementById("countdown");
                        const timer = setInterval(() => {
                            seconds--;
                            countdown.textContent = seconds;
                            if (seconds === 0) {
                                clearInterval(timer);
                                window.location.href = "${redirectUrl}";
                            }
                        }, 1000);
                    </script>
                </c:if>
            </c:otherwise>
        </c:choose>

    </body>
</html>

