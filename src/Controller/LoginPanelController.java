package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPanelController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    public void login() throws IOException {

        /*
        here should be login and password authorization
        with database
         */

        //placeholders for user informations
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String function = "manager";

        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));
        Parent parent = (Parent) loader.load();
        MainMenuController controller = loader.<MainMenuController>getController();
        controller.setParameters(login, password, function); //pass variables to MainMenuController

        stage.setScene(new Scene(parent, 1200, 800));
    }
}
