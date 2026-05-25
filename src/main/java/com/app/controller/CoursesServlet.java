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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/coursesServlet")
public class CoursesServlet extends HttpServlet {

    private final CoursesDAO courseDAO = new CoursesDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute(SessionConstants.STUDENT_SESSION);

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Course> courses = courseDAO.findAll();
            Set<Integer> enrolledCourseIds = new HashSet<>();

            for (Course course : courses) {
                if (enrollmentDAO.isEnrolled(student.getId(), course.getId())) {
                    enrolledCourseIds.add(course.getId());
                }
            }

            request.setAttribute("courses", courses);
            request.setAttribute("enrolledCourseIds", enrolledCourseIds);
            request.getRequestDispatcher("/courses.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error loading courses", e);
        }
    }
}