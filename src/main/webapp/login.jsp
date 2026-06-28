
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DiamondIQ | Login</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/auth.css">
</head>
<body class="auth-body">

<div class="auth-container">

    <%-- ======================================================
         Application Header
         ====================================================== --%>

    <div class="auth-header">
        <h1 class="auth-title">
            DiamondIQ
        </h1>

        <p class="auth-subtitle">
            MLB Analytics & Prediction Engine
        </p>
    </div>

    <%-- ======================================================
         Login Form
         ====================================================== --%>

    <div class="auth-card">

        <h2>
            Welcome Back
        </h2>

        <p class="auth-description">
            Sign in to continue to your dashboard.
        </p>

        <p class="auth-error">
            ${error}
        </p>

        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label for="username">
                    Username
                </label>

                <input
                    id="username"
                    name="username"
                    type="text"
                    value="${username}"
                    placeholder="Enter your username"
                    required>
            </div>

            <div class="form-group">
                <label for="password">
                    Password
                </label>

                <input
                    id="password"
                    name="password"
                    type="password"
                    placeholder="Enter your password"
                    required>
            </div>

            <button
                class="auth-button"
                type="submit">
                Login
            </button>
        </form>

        <%-- ==================================================
             Registration Link
             ================================================== --%>

        <div class="auth-footer">

            <p>
                New to DiamondIQ?
            </p>

            <a href="register.jsp">
                Create an Account
            </a>
        </div>
    </div>
</div>

</body>
</html>
