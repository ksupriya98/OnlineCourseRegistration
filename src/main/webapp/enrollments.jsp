<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Enrollments</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="nav-brand">Course Registration</div>
        <div class="nav-links">
            <span class="nav-user">Welcome, ${sessionScope.loggedInStudent.fullName}</span>
            <a href="${pageContext.request.contextPath}/courses">Courses</a>
            <a href="${pageContext.request.contextPath}/enrollments" class="active">My Enrollments</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Logout</a>
        </div>
    </nav>

    <div class="container">
        <h2>My Enrollments</h2>

        <c:if test="${param.success == 'dropped'}">
            <div class="alert alert-success">Course dropped successfully.</div>
        </c:if>

        <c:choose>
            <c:when test="${empty enrollments}">
                <p class="empty-state">
                    You haven't enrolled in any courses yet.
                    <a href="${pageContext.request.contextPath}/courses">Browse courses</a>
                </p>
            </c:when>
            <c:otherwise>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Course Name</th>
                            <th>Description</th>
                            <th>Enrolled On</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="enrollment" items="${enrollments}">
                            <tr>
                                <td>${enrollment.courseName}</td>
                                <td>${enrollment.courseDescription}</td>
                                <td>
                                    <fmt:formatDate value="${enrollment.enrolledAt}"
                                                    pattern="dd MMM yyyy, hh:mm a"/>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/drop" method="post"
                                          onsubmit="return confirm('Are you sure you want to drop this course?');">
                                        <input type="hidden" name="courseId" value="${enrollment.courseId}">
                                        <button type="submit" class="btn btn-danger btn-sm">Drop</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>