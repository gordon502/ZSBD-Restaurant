package Controller;

import Model.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UsersPanelController {

    @FXML
    private Label loggedUserLabel;

    @FXML
    private Button backButton;

    @FXML
    public void showMainMenu() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        Parent mainMenu = FXMLLoader.load(getClass().getResource("../View/MainMenuScene.fxml"));
        stage.setScene(new Scene(mainMenu, 1200, 800));
    }

    @FXML
    public void initialize(){
        loggedUserLabel.setText("Logged user: " + UserData.login);
    }
}
