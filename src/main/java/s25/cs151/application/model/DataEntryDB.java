package s25.cs151.application.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import s25.cs151.application.model.DataEntry;

public class DataEntryDB {
    private static final String url = "jdbc:sqlite:db/data_entry.db";
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS data_entry (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

    public static void updateData(DataEntry entry) {
        String sql = "UPDATE data_entry SET studentName = ?, time = ?, course = ?, \"date\" = ?, reason = ?, comments = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getStudentName());
            pstmt.setString(2, entry.getTime());
            pstmt.setString(3, entry.getCourse());
            pstmt.setString(4, entry.getDate());
            pstmt.setString(5, entry.getReason());
            pstmt.setString(6, entry.getComments());
            pstmt.setInt(7, entry.getId());

            pstmt.executeUpdate();

            System.out.println("Update successful!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
