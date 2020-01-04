package Controller;

import Model.User;
import Model.UserList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchedulePanelController {

    @FXML
    private Label weekLabel;

    @FXML
    private Button prevWeekButton;

    @FXML
    private Button nextWeekButton;

    @FXML
    private ComboBox<String> userCombo;

    @FXML
    private ComboBox<String> dayCombo;

    @FXML
    private ComboBox<?> startCombo;

    @FXML
    private ComboBox<?> endCombo;

    @FXML
    private Button addButton;

    @FXML
    private Button modButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button backButton;

    @FXML
    void modSchedule(ActionEvent event) {

    }

    @FXML
    void removeSchedule(ActionEvent event) {

    }

    @FXML
    void showMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));
        Parent mainMenu = (Parent) loader.load();
        MainMenuController controller = loader.<MainMenuController>getController();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void initialize() {
        ArrayList<String> userComboOptions = new ArrayList<String>();
        for (User user: UserList.users){
            userComboOptions.add(String.valueOf(user.getUserId())+" - "+user.getFirstName()+" "+user.getLastName());
        }
        userCombo.getItems().addAll((List)userComboOptions);
        dayCombo.getItems().addAll(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

    }
}
