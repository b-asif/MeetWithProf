package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import s25.cs151.application.model.DataEntry;
import s25.cs151.application.model.DataEntryDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EditEntryController {
    @FXML private TextField studentName;
    @FXML private ComboBox<String> course;
    @FXML private ComboBox<String> time;
    @FXML private DatePicker date;
    @FXML private TextArea reason;
    @FXML private  TextArea comment;

    private DataEntry entry;

    @FXML
    private void initialize() {
        List<String> timeSlots= getTimeSlots();
        List<String> courseSlot = getCourseList();
        if(time != null && !timeSlots.isEmpty()) {
            time.getItems().setAll(timeSlots);
            time.getSelectionModel().selectFirst();
        }
        if(course != null && !courseSlot.isEmpty()) {
            course.getItems().setAll(courseSlot);
            course.getSelectionModel().selectFirst();
        }
        if(date != null) {
            date.setValue(LocalDate.now());
        }
    }

    private List<String> getTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        String url = "jdbc:sqlite:db/time_slot.db";
        String query = "SELECT startTime, endTime from time_slot";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Loop through the result set and add time slots to the list
            while (resultSet.next()) {
                String start = resultSet.getString("startTime");
                String end = resultSet.getString("endTime");
                String timeSlot = start + "-" + end;
                timeSlots.add(timeSlot);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database connection errors
        }

        return timeSlots;

    }
    private List<String> getCourseList() {
        List<String> courseSelection = new ArrayList<>();
        String url = "jdbc:sqlite:db/course_info.db";
        String query = "SELECT courseCode from course_info";

        try(Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {

            while(resultSet.next()) {
                String course = resultSet.getString("courseCode");
                courseSelection.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseSelection;
    }

    public void SetInitialEntry(DataEntry entry) {
        this.entry = entry;

        if(entry!=null) {
            studentName.setText(entry.getStudentName());
            course.setValue(entry.getCourse());
            time.setValue(entry.getTime());
            date.setValue(LocalDate.parse(entry.getDate()));
            reason.setText(entry.getReason());
            comment.setText(entry.getComments());
        }
    }

    public void editEntry() {
        entry.setStudentName(studentName.getText());
        entry.setDate(date.getValue().toString());
        entry.setTime(time.getValue());
        entry.setCourse(course.getValue());
        entry.setComments(comment.getText());
        entry.setReason(reason.getText());
    }
}
