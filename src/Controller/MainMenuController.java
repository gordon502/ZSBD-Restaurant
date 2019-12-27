package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    //variables describing current user
    private String login;
    private String password;
    private String function;

    @FXML
    private Button logOutButton;

    @FXML
    private Label loggedUserLabel;

    public void setParameters(String login, String password, String function){
        this.login = login;
        this.password = password;
        this.function = function;
        loggedUserLabel.setText("Logged User: " + login);
    }

    @FXML
    public void logout() throws IOException {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        Parent mainMenu = FXMLLoader.load(getClass().getResource("../View/LoginPanelScene.fxml"));
        stage.setScene(new Scene(mainMenu, 1200, 800));
    }
}
