package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StockRoomPanelController {

    @FXML
    private GridPane dataGrid;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField quantityTextField;

    @FXML
    private TextField demandTextField;

    @FXML
    private Label userLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button showSuppliersButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<?> userTable;

    @FXML
    void addProduct(ActionEvent event) {

    }

    @FXML
    void modifyProduct(ActionEvent event) {

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
    void showSuppliers(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        modifyButton.setDisable(false);
        showSuppliersButton.setDisable(false);

        //force quantityTextField and demandTextField to get only numeric values
        quantityTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    quantityTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        demandTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    demandTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

}
