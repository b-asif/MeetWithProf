package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotsController {
    @FXML private ComboBox<String> startTime;
    @FXML private ComboBox<String> endTime;
    @FXML private Button addSlotButton;

    private Stage stage;
    private ObservableList<String> selectedTimeSlots = FXCollections.observableArrayList();

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void initialize() {
        ObservableList<String> timings = FXCollections.observableArrayList(timeSlots());
        startTime.setItems(timings);
        endTime.setDisable(true);

        startTime.setOnAction(event -> {
            String selectedTime = startTime.getValue();
            if(selectedTime != null) {
                endTime.setDisable(false);
                endTime.setItems(filterTime(timings, selectedTime));
                endTime.getSelectionModel().clearSelection();
            }
        });
        addSlotButton.setOnAction(event -> addTimeSlot());
    }
    private List<String> timeSlots() {
        List<String> time = new ArrayList<>();
        LocalTime localTime = LocalTime.of(0,0);
        while(!localTime.equals(LocalTime.of(23,45).minusMinutes(15))) {
            time.add(localTime.format(DateTimeFormatter.ofPattern("HH:mm a")));
            localTime = localTime.plusMinutes(15);
        }
        return time;
    }
    private ObservableList<String> filterTime(ObservableList<String> timing, String startTime) {
        return FXCollections.observableArrayList(timing.filtered(time -> time.compareTo(startTime) > 0));
    }

    private void addTimeSlot() {
        Alert storedInfo = new Alert(Alert.AlertType.INFORMATION);
        storedInfo.setTitle("Add another entry");
        storedInfo.setHeaderText(null);
        storedInfo.setContentText("Please select another time slot");
        storedInfo.showAndWait();
        String start = startTime.getValue();
        String end = endTime.getValue();

        selectedTimeSlots.add(start + " - " + end);
        startTime.getSelectionModel().clearSelection();
        endTime.getSelectionModel().clearSelection();
        endTime.setDisable(true);
    }

    @FXML
    private void submitTimeSlot() {
        if(startTime.getValue()!=null && endTime.getValue()!=null) {
            String lastSlot = startTime.getValue() + " - " + endTime.getValue();
            if(!selectedTimeSlots.contains(lastSlot)) {
                selectedTimeSlots.add(lastSlot);
            }
        }

        if(selectedTimeSlots.isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No time slots were added. Please select a time");
            return;
        }

        try {
            for (String slot : selectedTimeSlots) {
                String[] times = slot.split(" - ");
                TimeSlotDB.insertTimeSlot(times[0], times[1]);
            }

            Alert storedInfo = new Alert(Alert.AlertType.INFORMATION);
            storedInfo.setTitle("Time Slot Information Recorded");
            storedInfo.setHeaderText(null);
            storedInfo.setContentText("Visit the Schedule page to see all stored Time Slots");
            storedInfo.showAndWait();
            startTime.getSelectionModel().clearSelection();
            endTime.getSelectionModel().clearSelection();
        } catch (Exception e) {
            System.out.println("Database Error");
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
}


