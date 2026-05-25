package com.app.dao;

import com.app.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO {

    public List<Course> findAll() throws SQLException {
        String sql = "SELECT c.*, COUNT(e.id) AS enrolled_count "
                + "FROM courses c "
                + "LEFT JOIN enrollments e ON c.id = e.course_id "
                + "GROUP BY c.id "
                + "ORDER BY c.name";
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
        }
        return courses;
    }

    public Course findById(int id) throws SQLException {
        String sql = "SELECT c.*, COUNT(e.id) AS enrolled_count "
                + "FROM courses c "
                + "LEFT JOIN enrollments e ON c.id = e.course_id "
                + "WHERE c.id = ? "
                + "GROUP BY c.id";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public boolean create(Course course) throws SQLException {
        String sql = "INSERT INTO courses (name, description, capacity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, course.getName());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getCapacity());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Course course) throws SQLException {
        String sql = "UPDATE courses SET name = ?, description = ?, capacity = ? WHERE id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, course.getName());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getCapacity());
            ps.setInt(4, course.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Course mapRow(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setName(rs.getString("name"));
        course.setDescription(rs.getString("description"));
        course.setCapacity(rs.getInt("capacity"));
        course.setCreatedAt(rs.getTimestamp("created_at"));
        course.setEnrolledCount(rs.getInt("enrolled_count"));
        return course;
    }
}