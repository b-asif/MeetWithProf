package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import s25.cs151.application.model.CourseInfoDB;

import java.io.IOException;

public class CoursesController implements setStage{
    private final NavigationHandler navigatePage = new NavigationHandler();
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
