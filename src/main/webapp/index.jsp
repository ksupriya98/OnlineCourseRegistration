<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Course Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="hero">
            <h1>Online Course Registration</h1>
            <p>Register, login, and enroll in courses — all in one place.</p>
            <div class="hero-actions">
                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Register</a>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">Login</a>
            </div>
        </div>

        <div class="features">
            <div class="feature-card">
                <h3>Student Registration</h3>
                <p>Create your account with name, email, and password.</p>
            </div>
            <div class="feature-card">
                <h3>Secure Login</h3>
                <p>Access your dashboard with session-based authentication.</p>
            </div>
            <div class="feature-card">
                <h3>Course Selection</h3>
                <p>Browse available courses and enroll with one click.</p>
            </div>
        </div>
    </div>
</body>
</html>