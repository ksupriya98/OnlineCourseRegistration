package com.app.controller;

import com.app.dao.CoursesDAO;
import com.app.dao.EnrollmentDAO;
import com.app.model.Course;
import com.app.model.Student;
import util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/enroll")
public class EnrollServlet extends HttpServlet {

    private final CoursesDAO courseDAO = new CoursesDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute(SessionConstants.STUDENT_SESSION);

        String courseIdParam = request.getParameter("courseId");
        if (courseIdParam == null || courseIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/courses?error=invalid");
            return;
        }

        int courseId = Integer.parseInt(courseIdParam);

        try {
            Course course = courseDAO.findById(courseId);
            if (course == null) {
                response.sendRedirect(request.getContextPath() + "/courses?error=notfound");
                return;
            }

            if (course.isFull()) {
                response.sendRedirect(request.getContextPath() + "/courses?error=full");
                return;
            }

            if (enrollmentDAO.isEnrolled(student.getId(), courseId)) {
                response.sendRedirect(request.getContextPath() + "/courses?error=already");
                return;
            }

            enrollmentDAO.enroll(student.getId(), courseId);
            response.sendRedirect(request.getContextPath() + "/courses?success=enrolled");
        } catch (SQLException e) {
            throw new ServletException("Database error during enrollment", e);
        }
    }
}