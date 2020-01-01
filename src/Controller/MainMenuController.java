package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import Model.UserData;

public class MainMenuController {


    @FXML
    private Button logOutButton;

    @FXML
    private Label loggedUserLabel;

    @FXML
    public void showActiveBillsPanel() {

    }

    @FXML
    public void showNewBillPanel() {

    }

    @FXML
    public void showSchedulePanel() throws IOException {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        Parent schedulePanel = FXMLLoader.load(getClass().getResource("../View/SchedulePanelScene.fxml"));
        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(schedulePanel, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void showStockRoomPanel() {

    }

    @FXML
    public void showRaportsPanel() throws IOException{
        if (UserData.function.equals("manager")) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            Parent mainMenu = FXMLLoader.load(getClass().getResource("../View/RaportsPanelScene.fxml"));
            Scene oldScene = stage.getScene();
            stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
        }
        else {
            showAccessDeniedAlert();
        }
    }

    @FXML
    public void showUsersPanel() throws IOException{
        if (UserData.function.equals("manager")) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            Parent mainMenu = FXMLLoader.load(getClass().getResource("../View/UsersPanelScene.fxml"));
            Scene oldScene = stage.getScene();
            stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
        }
        else {
            showAccessDeniedAlert();
        }

    }

    @FXML
    public void logout() throws IOException {
        UserData.login = null;
        UserData.function = null;
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        Parent mainMenu = FXMLLoader.load(getClass().getResource("../View/LoginPanelScene.fxml"));
        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }



    @FXML
    public void initialize(){
        loggedUserLabel.setText("Logged user: " + UserData.login);
    }

    private void showAccessDeniedAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Access Denied!");
        alert.setHeaderText(null);
        alert.setContentText("You don't have permission for this operation!");
        alert.showAndWait();
    }
}
