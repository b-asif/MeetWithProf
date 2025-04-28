package s25.cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.stage.Stage;
import s25.cs151.application.controller.HomeController;
import s25.cs151.application.model.CourseInfoDB;
import s25.cs151.application.model.DataEntryDB;
import s25.cs151.application.model.OfficeHourDataBase;
import s25.cs151.application.model.TimeSlotDB;


import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        OfficeHourDataBase.createTable();
        CourseInfoDB.createTable();
        TimeSlotDB.createTable();
        DataEntryDB.createTable();

        FXMLLoader load = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/HomePage.fxml"));
        Scene home = new Scene(load.load(), 1920,1080);

        HomeController control = load.getController();
        control.setStage(stage);

        stage.setScene(home);
        stage.setTitle("Meet Your Professor");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}