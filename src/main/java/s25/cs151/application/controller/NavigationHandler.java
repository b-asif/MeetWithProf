package s25.cs151.application.controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import s25.cs151.application.controller.setStage;
import java.io.IOException;

public class NavigationHandler {
    //need each page to navigate to other pages
    public void navigate(String fxml, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Object controller = loader.getController();
            //we are going to check if the controller is going to implement the setStage interface
            if(controller instanceof setStage) {
                //if the controller implements the setStage interface we first cast the controller object to setStage
                // call the setStage method
                ((setStage) controller).setStage(stage);
            }
            stage.getScene().setRoot(root);
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();  // Debug loading errors
        }
    }
}
