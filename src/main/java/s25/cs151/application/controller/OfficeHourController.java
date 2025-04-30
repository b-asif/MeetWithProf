package s25.cs151.application.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import s25.cs151.application.model.OfficeHourDataBase;

import java.io.IOException;

public class OfficeHourController implements setStage {
    @FXML private ComboBox<String> semesterDropDown;
    @FXML private GridPane formContainer;
    @FXML private TextField year;
    @FXML private TextField startTime;
    @FXML private TextField endTime;
    @FXML private CheckBox mCheckBox;
    @FXML private CheckBox tCheckBox;
    @FXML private CheckBox wCheckBox;
    @FXML private CheckBox thCheckBox;
    @FXML private CheckBox fCheckBox;


    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private final NavigationHandler navigatePage = new NavigationHandler();

    @FXML
    private void submitOfficeHourSchedule() {

        String semester = semesterDropDown.getValue();
        String yearVal = year.getText();
        StringBuilder daysSelected = new StringBuilder();

        if(yearVal.length() != 4) {
            Alert invalidYear = new Alert(Alert.AlertType.ERROR);
            invalidYear.setTitle("Invalid year entry");
            invalidYear.setHeaderText("Please enter a valid 4-digit year.");
            invalidYear.showAndWait();
            return;
        }
        if(mCheckBox.isSelected()) {
            daysSelected.append("M, ");
        }
        if(tCheckBox.isSelected()) {
            daysSelected.append("T, ");
        }
        if(wCheckBox.isSelected()) {
            daysSelected.append("W, ");
        }
        if(thCheckBox.isSelected()) {
            daysSelected.append("TH, ");
        }
        if(fCheckBox.isSelected()) {
            daysSelected.append("F, ");
        }
        if(semester != null && !yearVal.isEmpty() && daysSelected.length() > 0) {
            OfficeHourDataBase.insertOfficeHourSchedule(semester, yearVal, daysSelected.toString());
            Alert storedInfo = new Alert(Alert.AlertType.INFORMATION);
            storedInfo.setTitle("Office Hour Recorded for the Semester!");
            storedInfo.setHeaderText(null);
            storedInfo.setContentText("Visit the Schedule page to see all stored Semester Office Hours");
            storedInfo.showAndWait();
        }
        else{
            System.out.println("You need to fill the form.");
        }
    }
    //everytime we change pages, we have the same code repeated throughout
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

    @FXML
    public void initialize() {
        semesterDropDown.getItems().addAll("Spring", "Summer", "Fall", "Winter");

    }

}