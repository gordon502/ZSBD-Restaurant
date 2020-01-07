package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Model.UserData;

import javax.jws.soap.SOAPBinding;

public class LoginPanelController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void login() throws IOException {

        /*
        here should be login and password authorization
        with database
         */


        //placeholders for user informations
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String function = "";

        //choiceDialog for debug purposes
        List<String> choices = new ArrayList<>();
        choices.add("manager");
        choices.add("employee");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("manager", choices);
        dialog.setTitle("Choice function // for debug");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose your function:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            function = result.get();
        }

        //should be written with database information after successfully authorization
        UserData.login = login;
        UserData.function = function;
        UserData.id = 1;

        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));
        Parent parent = (Parent) loader.load();
        MainMenuController controller = loader.<MainMenuController>getController();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(parent, oldScene.getWidth(), oldScene.getHeight()));
    }
}
