package Controller;

import Model.ConnectionData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import Model.UserData;

public class MainMenuController {

    @FXML
    private Button logOutButton;

    @FXML
    private Label loggedUserLabel;

    @FXML
    public void showActiveBillsPanel() throws IOException{
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(logOutButton, "../View/ActiveBillsPanelScene.fxml");
    }

    @FXML
    public void showNewBillPanel() {
        try {
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("INSERT INTO Orders " +
                    "VALUES(NULL, ?, ?, ?, 0, 0)");
            stmt.setInt(1, UserData.id); stmt.setString(2, UserData.login);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
            stmt.close();

            Alerts.showInformationAlert("Empty bill has been added!");
        }
        catch (SQLException e) {
            Alerts.showErrorAlert("Error, contact with administrator or try again later!");
        }
    }

    @FXML
    public void showSchedulePanel() throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(logOutButton, "../View/SchedulePanelScene.fxml");
    }

    @FXML
    public void showStockRoomPanel() throws IOException {
        if (UserData.function.equals("manager")) {
            SceneSwitcher ss = new SceneSwitcher();
            ss.switchScene(logOutButton, "../View/StockRoomPanelScene.fxml");
        }
        else {
            showAccessDeniedAlert();
        }
    }

    @FXML
    void showWorkshiftPanel() throws IOException {
        if (UserData.function.equals("manager")) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/WorkshiftPanelScene.fxml"));
            Parent workshiftPanel = (Parent) loader.load();

            Scene oldScene = stage.getScene();
            stage.setScene(new Scene(workshiftPanel, oldScene.getWidth(), oldScene.getHeight()));
        }
        else {
            showAccessDeniedAlert();
        }
    }

    @FXML
    public void showEditItemPanel() throws IOException{
        if (UserData.function.equals("manager")) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EditItemPanelScene.fxml"));
            Parent editItemPanel = (Parent) loader.load();

            Scene oldScene = stage.getScene();
            stage.setScene(new Scene(editItemPanel, oldScene.getWidth(), oldScene.getHeight()));
        }
        else {
            showAccessDeniedAlert();
        }

    }

    @FXML
    public void showRaportsPanel() throws IOException{
        if (UserData.function.equals("manager")) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/RaportsPanelScene.fxml"));
            Parent raportsPanel = (Parent) loader.load();
            RaportsPanelController controller = loader.<RaportsPanelController>getController();

            Scene oldScene = stage.getScene();
            stage.setScene(new Scene(raportsPanel, oldScene.getWidth(), oldScene.getHeight()));
        }
        else {
            showAccessDeniedAlert();
        }
    }

    @FXML
    public void showUsersPanel() throws IOException{
        if (UserData.function.equals("manager")) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/UsersPanelScene.fxml"));
            Parent usersPanel = (Parent) loader.load();
            UsersPanelController controller = loader.<UsersPanelController>getController();

            Scene oldScene = stage.getScene();
            stage.setScene(new Scene(usersPanel, oldScene.getWidth(), oldScene.getHeight()));
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/LoginPanelScene.fxml"));
        Parent loginPanel = (Parent) loader.load();
        LoginPanelController controller = loader.<LoginPanelController>getController();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(loginPanel, oldScene.getWidth(), oldScene.getHeight()));
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
