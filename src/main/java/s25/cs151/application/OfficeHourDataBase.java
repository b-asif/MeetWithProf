package s25.cs151.application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OfficeHourDataBase {
    private static final String url = "jdbc:sqlite:db/office_hours.db";
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS office_hours (" +
                "semester TEXT NOT NULL, " +
                "year TEXT NOT NULL, " +
                "days TEXT" +
                ");";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
            System.out.println("Table created or already exists!");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    public static void insertOfficeHourSchedule(String semester, String year, String days) {
        String sql = "INSERT INTO office_hours (semester, year, days) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, semester);
            pstmt.setString(2, year);
            pstmt.setString(3, days);

            pstmt.executeUpdate();

            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
