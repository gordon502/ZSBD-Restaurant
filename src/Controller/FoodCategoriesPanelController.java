package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class FoodCategoriesPanelController {

    @FXML
    private GridPane dataGrid;

    @FXML
    private TextField discountCodeTextField;

    @FXML
    private TextField valueTextField;

    @FXML
    private TextField foodCategoryTextField;

    @FXML
    private Label userLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<?> supplierTable;

    @FXML
    void addFoodCategory(ActionEvent event) {

    }

    @FXML
    void modifyFoodCategory(ActionEvent event) {

    }

    @FXML
    void showEditItemPanel(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/EditItemPanelScene.fxml");
    }

}
