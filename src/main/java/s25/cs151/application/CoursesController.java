package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class CoursesController {
    @FXML private TextField courseCode;
    @FXML private TextField courseName;
    @FXML private TextField sectionNumber;

    Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void submitCourseInfo() {
        String code = courseCode.getText();
        String name = courseName.getText();
        String section = sectionNumber.getText();

        if(!code.isEmpty() && !name.isEmpty() && !section.isEmpty()) {
            try {
                CourseInfoDB.insertCourseInfo(code, name, section);
                Alert storedInfo = new Alert(Alert.AlertType.INFORMATION);
                storedInfo.setTitle("Course Information Recorded");
                storedInfo.setHeaderText(null);
                storedInfo.setContentText("Visit the Schedule page to see all stored Semester Office Hours");
                storedInfo.showAndWait();

                courseCode.clear();
                courseName.clear();
                sectionNumber.clear();
            }
            catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Duplicate Entry");
                error.setHeaderText(null);
                error.setContentText("A course with this code, name, and section already exists.");
                error.showAndWait();
            }
        }
        else{
            System.out.println("You need to fill the form.");
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
    private void goToOfficeHoursPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("office_hour.fxml"));
            Parent root = loader.load();
            OfficeHourController control = loader.getController();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DataEntry.fxml"));
            Parent root = loader.load();
            DataEntryController control = loader.getController();
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
