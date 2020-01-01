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
import java.sql.Connection;

public class UsersPanelController {

    Connection conn = null;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private Button backButton;

    public void setDBConnection(Connection conn){
        this.conn=conn;
    }

    @FXML
    public void showMainMenu() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));

        MainMenuController controller = loader.<MainMenuController>getController();
        controller.setDBConnection(conn);

        Parent mainMenu = (Parent) loader.load();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void showNewUserPanel() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/NewUserPanelScene.fxml"));

        MainMenuController controller = loader.<MainMenuController>getController();
        controller.setDBConnection(conn);

        Parent newUserPanel = (Parent) loader.load();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(newUserPanel, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void initialize() {
        loggedUserLabel.setText("Logged user: " + UserData.login);
    }


}
