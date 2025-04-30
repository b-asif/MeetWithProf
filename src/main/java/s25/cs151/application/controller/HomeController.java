package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;


import java.io.IOException;

public class HomeController implements setStage {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final NavigationHandler navigatePage = new NavigationHandler();

    @FXML
    private void goToOfficeHoursPage() throws IOException {
        navigatePage.navigate("/s25/cs151/application/view/office_hour.fxml", stage);
    }
    @FXML
    private void goToTableView() {
        navigatePage.navigate("/s25/cs151/application/view/TableView.fxml", stage);
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
    private void goToDataEntry() {
        navigatePage.navigate("/s25/cs151/application/view/DataEntry.fxml", stage);
    }

}