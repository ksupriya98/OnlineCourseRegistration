package com.app.controller;

import com.app.dao.EnrollmentDAO;
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

@WebServlet("/dropServlet")
public class DropServlet extends HttpServlet {

    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute(SessionConstants.STUDENT_SESSION);

        String courseIdParam = request.getParameter("courseId");
        if (courseIdParam == null || courseIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/enrollments?error=invalid");
            return;
        }

        int courseId = Integer.parseInt(courseIdParam);

        try {
            enrollmentDAO.drop(student.getId(), courseId);
            response.sendRedirect(request.getContextPath() + "/enrollments?success=dropped");
        } catch (SQLException e) {
            throw new ServletException("Database error dropping course", e);
        }
    }
}