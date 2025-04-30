package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import s25.cs151.application.model.DataEntryDB;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataEntryController implements setStage {
    private final NavigationHandler navigatePage = new NavigationHandler();

    @FXML private TextField studentName;
    @FXML private ComboBox<String> startTime;
    @FXML private ComboBox<String> courses;
    @FXML private DatePicker datePicker;
    @FXML private TextField reason;
    @FXML private TextField comments;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        List<String> timeSlots = getTimeSlots();
        List<String> courseSlot = getCourseList();

        if(startTime != null && !timeSlots.isEmpty()) {
            startTime.getItems().setAll(timeSlots);
            startTime.getSelectionModel().selectFirst();
        }
        if(courses != null && !courseSlot.isEmpty()) {
            courses.getItems().setAll(courseSlot);
            courses.getSelectionModel().selectFirst();
        }
        if(datePicker != null) {
            datePicker.setValue(LocalDate.now());
        }
    }

    @FXML
    private void handlesubmit() {
        String name = studentName.getText();
        String time = startTime.getValue();
        String course = courses.getValue();
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
        String text = reason.getText();
        String comment = comments.getText();

        StringBuilder error = new StringBuilder();

        if(studentName.getText().isEmpty()) {
            error.append("Please enter name");
        }
        if(startTime.getValue() == null) {
            error.append("Select a time.\n");
        }
        if (courses.getValue() == null) {
            error.append("Select a course.\n");
        }
        if(datePicker.getValue() == null) {
            error.append("Enter date.\n");
        }

        if(error.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing fields");
            alert.setHeaderText("Please fill out the missing fields");
            alert.setContentText(error.toString());
            alert.showAndWait();
        }
        else {
            DataEntryDB.insertDataEntry(name, time, course, date, text, comment);
            Alert storedInfo = new Alert(Alert.AlertType.INFORMATION);
            storedInfo.setTitle("Course Information Recorded");
            storedInfo.setHeaderText(null);
            storedInfo.setContentText("Visit the Schedule page to see all stored Semester Office Hours");
            storedInfo.showAndWait();
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

    @FXML
    private void goToDashboard() {
        navigatePage.navigate("/s25/cs151/application/view/HomePage.fxml", stage);
    }
    @FXML
    private void goToTableView() {
        navigatePage.navigate("/s25/cs151/application/view/TableView.fxml", stage);
    }
    @FXML
    private void goToOfficeHoursPage() {
        navigatePage.navigate("/s25/cs151/application/view/office_hour.fxml", stage);
    }
    @FXML
    private void goToDataEntry() {
        navigatePage.navigate("/s25/cs151/application/view/DataEntry.fxml", stage);
    }
    @FXML
    private void goToCourseSelection() {
        navigatePage.navigate("/s25/cs151/application/view/courses.fxml", stage);
    }
    @FXML
    private void goToTimeSlot() {
        navigatePage.navigate("/s25/cs151/application/view/TimeSlots.fxml", stage);
    }


}
