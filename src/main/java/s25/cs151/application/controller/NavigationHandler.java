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
            if(controller instanceof setStage) {
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
