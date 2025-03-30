package s25.cs151.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseInfoDB {
    private static final String url = "jdbc:sqlite:course_info.db";
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS course_info (" +
                "courseCode TEXT NOT NULL, " +
                "courseName TEXT NOT NULL, " +
                "sectionNum TEXT, " +
                "PRIMARY KEY (courseCode, sectionNum)" +
                ");";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
            System.out.println("Table created or already exists for courses!");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    public static void insertCourseInfo(String courseCode, String courseName, String sectionNum) throws Exception {
        String sql = "INSERT INTO course_info (courseCode, courseName, sectionNum) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseCode);
            pstmt.setString(2, courseName);
            pstmt.setString(3, sectionNum);

            pstmt.executeUpdate();

            System.out.println("Course data inserted successfully!");

        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMARY KEY constraint failed")) {
                throw new Exception("This entry is already stored.");
            }
            System.out.println(e.getMessage());
        }

    }

}


