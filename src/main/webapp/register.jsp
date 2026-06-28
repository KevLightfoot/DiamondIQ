<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DiamondIQ | Register</title>
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
         Registration Form
         ====================================================== --%>

    <div class="auth-card">
        <h2>
            Create Your Account
        </h2>

        <p class="auth-description">
            Join DiamondIQ and start predicting MLB matchups.
        </p>

        <p class="auth-error">
            ${error}
        </p>

        <form action="RegisterServlet" method="post">
            <div class="form-group">
                <label for="username">
                    Username
                </label>

                <input
                    id="username"
                    name="username"
                    type="text"
                    value="${username}"
                    placeholder="Choose a username"
                    required>
            </div>

            <div class="form-group">
                <label for="email">
                    Email
                </label>

                <input
                    id="email"
                    name="email"
                    type="email"
                    value="${email}"
                    placeholder="Enter your email"
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
                    placeholder="Create a password"
                    required>
            </div>

            <div class="form-group">
                <label for="confirm">
                    Confirm Password
                </label>

                <input
                    id="confirm"
                    name="confirm"
                    type="password"
                    placeholder="Confirm your password"
                    required>
            </div>

            <button
                class="auth-button"
                type="submit">
                Create Account
            </button>
        </form>

        <%-- ==================================================
             Login Link
             ================================================== --%>

        <div class="auth-footer">
            <p>
                Already have an account?
            </p>

            <a href="login.jsp">
                Login
            </a>
        </div>
    </div>
</div>

</body>
</html>
