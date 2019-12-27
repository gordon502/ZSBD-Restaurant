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
    public void generateRaport() {
        /*
        query raport from database from chosen period of time
        and show it on screen (i would recommend use Alert class)
        */
    }

    @FXML
    public void showMainMenu() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent mainMenu = FXMLLoader.load(getClass().getResource("../View/MainMenuScene.fxml"));
        stage.setScene(new Scene(mainMenu, 1200, 800));
    }

    @FXML
    public void initialize() {
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        loggedUserLabel.setText("Logged user: " + UserData.login);
    }
}
