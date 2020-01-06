package Controller;

import Model.ConnectionData;
import Model.Supplier;
import Model.SupplierList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class StockRoomPanelController {

    @FXML
    private GridPane dataGrid;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField demandTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Label userLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> supplierComboBox;

    @FXML
    private TableView<?> stockItemTable;

    @FXML
    void addProduct(ActionEvent event) {
        String name;
        Integer quantity;
        Integer demand;
        boolean dataFlag = true;

        if (!nameTextField.getText().equals("")) { name = nameTextField.getText(); }
        else { dataFlag = false; }

        try { quantity = Integer.valueOf(quantityTextField.getText()); }
        catch (NumberFormatException e) { dataFlag = false; }

        try { demand = Integer.valueOf(demandTextField.getText()); }
        catch (NumberFormatException e) { dataFlag = false; }

        //if data are correct
        if (dataFlag) {
            //insert into database
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Entered data is incorrect!");

            alert.showAndWait();
        }


    }

    @FXML
    void modifyProduct(ActionEvent event) {

    }

    @FXML
    void showMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));
        Parent mainMenu = (Parent) loader.load();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    void showSuppliers(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        modifyButton.setDisable(false);


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

        for (Supplier sup : SupplierList.suppliers) {
            supplierComboBox.getItems().add(sup.getSupplierId() + ". " + sup.getName());
        }



    }

}
