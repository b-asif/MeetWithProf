package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import s25.cs151.application.model.CourseInfo;
import s25.cs151.application.model.DataEntry;
import s25.cs151.application.model.SemesterInfo;
import s25.cs151.application.model.TimeSlot;

import java.io.IOException;
import java.sql.*;

public class TableViewController  {
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML private TableView<SemesterInfo> semesterInfoTableView;
    @FXML private TableView<CourseInfo> courseInfoTableView;
    @FXML private TableView<TimeSlot> timeSlotTableView;
    @FXML private TableView<DataEntry> dataEntryTableView;
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

    //Data Entry columns
    @FXML TableColumn<DataEntry, String> studentName;
    @FXML TableColumn<DataEntry, String> time;
    @FXML TableColumn<DataEntry, String> course;
    @FXML TableColumn<DataEntry, String> date;
    @FXML TableColumn<DataEntry, String> reason;
    @FXML TableColumn<DataEntry, String> comments;
    //search bar
    @FXML TextField searchField;
    @FXML Button searchButton;


    ObservableList<SemesterInfo> semesterOfficeHour = FXCollections.observableArrayList();
    ObservableList<CourseInfo> courseInfo = FXCollections.observableArrayList();
    ObservableList<TimeSlot> timeSlot = FXCollections.observableArrayList();
    ObservableList<DataEntry> dataEntries = FXCollections.observableArrayList();

    //filtering for search
    FilteredList<DataEntry> filteredData;

    @FXML
    public void initialize() {
        setUpTable();
        loadTable();
        setUpSearch();
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

        //data entry table
        studentName.setCellValueFactory((new PropertyValueFactory<>("studentName")));
        time.setCellValueFactory((new PropertyValueFactory<>("time")));
        course.setCellValueFactory((new PropertyValueFactory<>("course")));
        date.setCellValueFactory((new PropertyValueFactory<>("date")));
        reason.setCellValueFactory((new PropertyValueFactory<>("reason")));
        comments.setCellValueFactory((new PropertyValueFactory<>("comments")));

        dataEntryTableView.setItems(dataEntries);

    }
    private void loadTable() {
        loadSemesterData();
        loadCourseData();
        loadTimeSlot();
        loadDataEntries();
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
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db/office_hours.db");
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
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:db/course_info.db");
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
    try(Connection conn = DriverManager.getConnection("jdbc:sqlite:db/time_slot.db");
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

    private void loadDataEntries() {
        dataEntries.clear();
        String query = "SELECT * FROM data_entry ORDER BY date DESC, time DESC";
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:db/data_entry.db");
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query)) {
            while(result.next()) {
                dataEntries.add(new DataEntry(
                        result.getString("studentName"),
                        result.getString("time"),
                        result.getString("course"),
                        result.getString("date"),
                        result.getString("reason"),
                        result.getString("comments")
                ));
            }
            dataEntryTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void setUpSearch() {
        //when user searches for student
        filteredData = new FilteredList<>(dataEntries, p->true);
        SortedList<DataEntry> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(dataEntryTableView.comparatorProperty());
        dataEntryTableView.setItems(sortedData);

        searchField.setOnAction(e-> {
            handleSearch();
        });
    }

    @FXML
    private void handleSearch() {
        String input = searchField.getText().toLowerCase();

        filteredData.setPredicate(e -> {
            if(input == null || input.isEmpty()) return true;

            return e.getStudentName().toLowerCase().contains(input);
        });
    }

    @FXML
    private void handleDelete() {
        DataEntry selectedEntry = dataEntryTableView.getSelectionModel().getSelectedItem();
        if(selectedEntry == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No entry selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an office hour entry to remove");
            alert.showAndWait();
            return;
        }

        Alert confirmDeletion = new Alert(Alert.AlertType.WARNING);
        confirmDeletion.setTitle("Delete Confirmation");
        confirmDeletion.setHeaderText(null);
        confirmDeletion.setContentText("Are you sure you would like to delete this office hour for " + selectedEntry.getStudentName() + "?");
        confirmDeletion.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                deleteDataEntry(selectedEntry);
                dataEntries.remove(selectedEntry);
            }
        });
    }

    @FXML
    private void handleEdit() {
        DataEntry selectedEntry = dataEntryTableView.getSelectionModel().getSelectedItem();

        if(selectedEntry == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No entry selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an office hour entry to edit");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/EditEntry.fxml"));
            Parent editdialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(editdialogPane);
            dialog.setTitle("Edit entry");

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            EditEntryController entry = loader.getController();
            entry.SetInitialEntry(selectedEntry);

            var result = dialog.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                entry.editEntry();
                dataEntryTableView.refresh();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteDataEntry(DataEntry entry) {
        String query = "DELETE FROM data_entry WHERE studentName = ? AND date = ? AND time = ?";
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:db/data_entry.db");
            PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, entry.getStudentName());
            stmt.setString(2, entry.getDate());
            stmt.setString(3, entry.getTime());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void goToCourseSelection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/courses.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/office_hour.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/HomePage.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/TimeSlots.fxml"));
            Parent root = loader.load();
            TimeSlotsController control = loader.getController();
            control.setStage(stage);
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToDataEntry() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/DataEntry.fxml"));
            Parent root = loader.load();
            DataEntryController control = loader.getController();
            control.setStage(stage);
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}