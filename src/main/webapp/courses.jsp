<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Courses</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <nav class="navbar">
        <div class="nav-brand">Course Registration</div>
        <div class="nav-links">
            <span class="nav-user">
                Welcome, ${sessionScope.loggedInStudent.fullName}
            </span>
            <a href="${pageContext.request.contextPath}/courses" class="active">Courses</a>
            <a href="${pageContext.request.contextPath}/enrollments">My Enrollments</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Logout</a>
        </div>
    </nav>

    <div class="container">
        <h2>Available Courses</h2>

        <!-- Success / Error Messages -->
        <c:if test="${param.success eq 'enrolled'}">
            <div class="alert alert-success">Successfully enrolled in the course!</div>
        </c:if>

        <c:if test="${param.error eq 'full'}">
            <div class="alert alert-error">This course is full.</div>
        </c:if>

        <c:if test="${param.error eq 'already'}">
            <div class="alert alert-error">Already enrolled.</div>
        </c:if>

        <!-- Course List -->
        <c:choose>

            <c:when test="${empty courses}">
                <p>No courses available.</p>
            </c:when>

            <c:otherwise>
                <div class="course-grid">

                    <c:forEach var="course" items="${courses}">
                        <div class="course-card">

                            <h3>${course.name}</h3>
                            <p>${course.description}</p>

                            <div class="course-meta">
                                <span>
                                    Seats: ${course.enrolledCount} / ${course.capacity}
                                </span>

                                <c:if test="${course.full}">
                                    <span class="badge badge-full">Full</span>
                                </c:if>
                            </div>

                            <c:choose>

                                <c:when test="${enrolledCourseIds.contains(course.id)}">
                                    <button disabled>Enrolled</button>
                                </c:when>

                                <c:when test="${course.full}">
                                    <button disabled>Full</button>
                                </c:when>

                                <c:otherwise>
                                    <form action="${pageContext.request.contextPath}/enroll" method="post">
                                        <input type="hidden" name="courseId" value="${course.id}">
                                        <button type="submit">Enroll</button>
                                    </form>
                                </c:otherwise>

                            </c:choose>

                        </div>
                    </c:forEach>

                </div>
            </c:otherwise>

        </c:choose>
    </div>

</body>
</html>