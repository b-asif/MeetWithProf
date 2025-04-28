module com.example.meetyourprof {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
    exports s25.cs151.application.model;
    opens s25.cs151.application.model to javafx.fxml;
    exports s25.cs151.application.controller;
    opens s25.cs151.application.controller to javafx.fxml;
}