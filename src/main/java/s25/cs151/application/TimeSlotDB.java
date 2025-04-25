package s25.cs151.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TimeSlotDB {
    private static final String url = "jdbc:sqlite:db/time_slot.db";
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS time_slot (" +
                "startTime TEXT NOT NULL, " +
                "endTime TEXT NOT NULL " +
                ");";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
            System.out.println("Table created or already exists for time slot!");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    public static void insertTimeSlot(String startTime, String endTime) {
        String sql = "INSERT INTO time_slot (startTime, endTime) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, startTime);
            pstmt.setString(2, endTime);

            pstmt.executeUpdate();

            System.out.println("Time slot data was inserted successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
