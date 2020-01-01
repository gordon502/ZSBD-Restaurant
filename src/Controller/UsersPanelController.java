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
        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void showNewUserPanel() throws IOException{
        Stage stage = (Stage) backButton.getScene().getWindow();

        Parent newUserPanel = FXMLLoader.load(getClass().getResource("../View/NewUserPanelScene.fxml"));
        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(newUserPanel, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void initialize(){
        loggedUserLabel.setText("Logged user: " + UserData.login);
    }


}
