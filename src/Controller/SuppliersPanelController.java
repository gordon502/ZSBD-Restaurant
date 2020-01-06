package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SuppliersPanelController {

    @FXML
    private Text productText;

    @FXML
    private GridPane dataGrid;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Label userLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<?> userTable;

    @FXML
    void addProduct(ActionEvent event) {

    }

    @FXML
    void deleteSupplier(ActionEvent event) {

    }

    @FXML
    void modifySupplier(ActionEvent event) {

    }

    @FXML
    void showStockRoomPanel(ActionEvent event) {

    }

}
