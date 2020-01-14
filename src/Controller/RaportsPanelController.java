package Controller;

import Model.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class RaportsPanelController {

    @FXML
    private Label loggedUserLabel;

    @FXML
    private Button backButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    public void generateRaport() throws SQLException {
        Alerts.showRaportAlert(Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()));
    }

    @FXML
    public void showMainMenu() throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/MainMenuScene.fxml");
    }

    @FXML
    public void initialize() {
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        loggedUserLabel.setText("Logged user: " + UserData.login);
    }
}
