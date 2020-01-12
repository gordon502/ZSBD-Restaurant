package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {


    public void switchScene(Button button, String path) throws IOException {
        Stage window = (Stage) button.getScene().getWindow();
        Scene oldScene = window.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = (Parent) loader.load();

        window.setScene(new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
        window.show();

    }
}
