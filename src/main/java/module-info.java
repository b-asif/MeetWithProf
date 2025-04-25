module com.example.meetyourprofcopy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
}