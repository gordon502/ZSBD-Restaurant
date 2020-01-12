package Controller;

import Model.FoodItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EditItemPanelController {

    @FXML
    private TableView<FoodItem> activeFoodItemTable;

    @FXML
    private TableView<FoodItem> unactiveFoodItemTable;

    @FXML
    private GridPane dataGrid;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField vatTextField;

    @FXML
    private ComboBox<?> foodCategoryComboBox;

    @FXML
    private Label userLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button activeButton;

    @FXML
    private Button foodCategoriesButton;

    @FXML
    private Button backButton;

    @FXML
    void activeButton(ActionEvent event) {

    }

    @FXML
    void addItem(ActionEvent event) {

    }

    @FXML
    void modifyItem(ActionEvent event) {

    }

    @FXML
    void showFoodCategoriesPanel(ActionEvent event) throws IOException{
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/FoodCategoriesPanelScene.fxml");
    }

    @FXML
    void showMainMenu(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/MainMenuScene.fxml");
    }

    @FXML
    void initialize() {
        modifyButton.setDisable(true);

    }

}
