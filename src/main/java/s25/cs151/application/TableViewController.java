package s25.cs151.application;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TableViewController  {
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML private TableView<SemesterInfo> semesterInfoTableView;
    @FXML private TableView<CourseInfo> courseInfoTableView;
    @FXML private TableView<TimeSlot> timeSlotTableView;
    //columns for semester table
    @FXML
    private TableColumn<SemesterInfo, String> semester;
    @FXML
    private TableColumn<SemesterInfo, String> year;
    @FXML
    private TableColumn<SemesterInfo, String> days;

    //columns for course information
    @FXML private TableColumn<CourseInfo, String> courseCode;
    @FXML private TableColumn<CourseInfo, String> courseName;
    @FXML private TableColumn<CourseInfo, String> sectionNum;

    @FXML TableColumn<TimeSlot, String> startTime;
    @FXML TableColumn<TimeSlot, String> endTime;

    ObservableList<SemesterInfo> semesterOfficeHour = FXCollections.observableArrayList();
    ObservableList<CourseInfo> courseInfo = FXCollections.observableArrayList();
    ObservableList<TimeSlot> timeSlot = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setUpTable();
        loadTable();
    }

    private void setUpTable() {
        semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        days.setCellValueFactory(new PropertyValueFactory<>("days"));
        semesterInfoTableView.setItems(semesterOfficeHour);

        //courses table
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        sectionNum.setCellValueFactory(new PropertyValueFactory<>("sectionNum"));
        courseInfoTableView.setItems((courseInfo));


        //time slot table
        startTime.setCellValueFactory((new PropertyValueFactory<>("startTime")));
        endTime.setCellValueFactory((new PropertyValueFactory<>("endTime")));
        timeSlotTableView.setItems(timeSlot);

    }
    private void loadTable() {
        loadSemesterData();
        loadCourseData();
        loadTimeSlot();
    }
    private void loadSemesterData() {
        semesterOfficeHour.clear();
        String query = "SELECT * FROM office_hours " + "ORDER BY year DESC, " +
                "CASE semester " +
                "WHEN 'Winter' THEN 1 " +
                "WHEN 'Fall' THEN 2 " +
                "WHEN 'Summer' THEN 3 " +
                "WHEN 'Spring' THEN 4 " +
                "END ASC;";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:office_hours.db");
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(query)) {
            while(result.next()) {
                semesterOfficeHour.add(new SemesterInfo(
                        result.getString("semester"),
                        result.getString("year"),
                        result.getString("days")

                ));
            }
            semesterInfoTableView.refresh();

        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }
    private void loadCourseData() {
        courseInfo.clear();
        String query = "SELECT * FROM course_info " + "ORDER BY courseCode DESC;";
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:course_info.db");
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query)) {
                while(result.next()) {
                    courseInfo.add(new CourseInfo(
                            result.getString("courseCode"),
                            result.getString("courseName"),
                            result.getString("sectionNum")
                    ));
                }
                courseInfoTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
    }


}
private void loadTimeSlot() {
    timeSlot.clear();
    String query = "SELECT * FROM time_slot ORDER BY startTime ASC;";
    try(Connection conn = DriverManager.getConnection("jdbc:sqlite:time_slot.db");
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query)) {
        while(result.next()) {
            timeSlot.add(new TimeSlot(
                    result.getString("startTime"),
                    result.getString("endTime")
            ));
        }
        timeSlotTableView.refresh();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    @FXML
    private void goToDataBase() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("database.fxml"));
            Parent root = loader.load();
            TableViewController control = loader.getController();
            control.setStage(stage);
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToCourseSelection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("courses.fxml"));
            Parent root = loader.load();
            CoursesController control = loader.getController();
            control.setStage(stage);
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToTableView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tableView.fxml"));
            Parent root = loader.load();
            TableViewController control = loader.getController();
            control.setStage(stage);
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToOfficeHoursPage() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("office_hour.fxml"));
            Parent root = loader.load();
            OfficeHourController officeHourController = loader.getController();
            officeHourController.setStage(stage); // Pass the stage
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Debug loading errors
        }

    }
    @FXML
    private void goToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();
            HomeController control = loader.getController();
            control.setStage(stage);
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToTimeSlot() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TimeSlots.fxml"));
            Parent root = loader.load();
            TimeSlotsController control = loader.getController();
            control.setStage(stage);
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}