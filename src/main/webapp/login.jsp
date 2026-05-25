<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Online Course Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="form-card">
            <h2>Student Login</h2>

            <c:if test="${param.logout == 'true'}">
                <div class="alert alert-success">You have been logged out successfully.</div>
            </c:if>

            <c:if test="${param.error == 'session'}">
                <div class="alert alert-error">Please login to continue. Your session has expired.</div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email"
                           value="${email}" required placeholder="Enter your email">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password"
                           required placeholder="Enter password">
                </div>
                <button type="submit" class="btn btn-primary btn-block">Login</button>
            </form>

            <p class="form-footer">
                Don't have an account?
                <a href="${pageContext.request.contextPath}/register">Register here</a>
            </p>
        </div>
    </div>
</body>
</html>