package Controller;

import Model.WorkshiftItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class WorkshiftPanelController {
    ArrayList time;

    @FXML
    private ComboBox<String> userCombo;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button genButton;

    @FXML
    private Button countButton;

    @FXML
    private ComboBox<String> endCombo;

    @FXML
    private ComboBox<String> startCombo;

    @FXML
    private DatePicker selDate;

    @FXML
    private Button addmodButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<WorkshiftItem> workshiftTable;

    @FXML
    void addmod(ActionEvent event) {

    }

    @FXML
    void countHours(ActionEvent event) {

    }

    @FXML
    void removeWorkshift(ActionEvent event) {

    }

    @FXML
    void scheduleGenerate(ActionEvent event) {

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

        time = new ArrayList();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                time.add("0" + String.valueOf(i) + ":00");
            } else {
                time.add(String.valueOf(i) + ":00");
            }
        }
        startCombo.getItems().addAll(time);
        time.add("24:00");

        startCombo.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                endCombo.getItems().clear();
                for (int i = newValue.intValue() + 1; i < time.size(); i++) {
                    endCombo.getItems().add(time.get(i).toString());
                }

            }
        });

        startDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today.minusDays(1)) > 0);
            }
        });


        endDate.setDisable(true);

        startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            endDate.setDisable(false);
            endDate.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(startDate.getValue()) < 0 || date.compareTo(today.minusDays(1)) > 0);
                }
            });
        });

        selDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0);
            }
        });


    }
}
