package entities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcherFacade {
    private Stage stage;

    public SceneSwitcherFacade(Stage stage) {
        this.stage = stage;
    }

    public void switchScene(ActionEvent event, String sceneFXML) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
