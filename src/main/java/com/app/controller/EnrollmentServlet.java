package com.app.controller;

import com.app.dao.EnrollmentDAO;
import com.app.model.Enrollment;
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
import java.util.List;

@WebServlet("/enrollmentServlet")
public class EnrollmentServlet extends HttpServlet {

    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute(SessionConstants.STUDENT_SESSION);

        try {
            List<Enrollment> enrollments = enrollmentDAO.findByStudentId(student.getId());
            request.setAttribute("enrollments", enrollments);
            request.getRequestDispatcher("/enrollments.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error loading enrollments", e);
        }
    }
}