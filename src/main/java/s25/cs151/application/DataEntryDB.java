package s25.cs151.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataEntryDB {
    private static final String url = "jdbc:sqlite:data_entry.db";
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS data_entry (" +
                "studentName TEXT NOT NULL, " +
                "time TEXT NOT NULL, " +
                "course TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "reason TEXT, " +
                "comments TEXT" +
                ");";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
            System.out.println("Table created or already exists for data entry !");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    public static void insertDataEntry(String studentName, String time, String course, String date, String reason, String comments) {
        String sql = "INSERT INTO data_entry (studentName, time, course, date, reason, comments) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentName);
            pstmt.setString(2, time);
            pstmt.setString(3, course);
            pstmt.setString(4, date);
            pstmt.setString(5, reason);
            pstmt.setString(6, comments);

            pstmt.executeUpdate();

            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
