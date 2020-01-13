package Controller;

import Model.ConnectionData;
import Model.FoodItem;
import Model.FoodItemList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import javax.xml.soap.Text;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditItemPanelController {

    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<FoodItem> activeFoodItemsTable;

    @FXML
    private TableView<FoodItem> unactiveFoodItemsTable;

    @FXML
    private GridPane dataGrid;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField vatTextField;

    @FXML
    private ComboBox<String> foodCategoryComboBox;

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
    void addItem(ActionEvent event) throws SQLException{
        if (checkData()) {
            String name = nameTextField.getText();
            int price = Integer.valueOf(priceTextField.getText());
            String foodCategory = foodCategoryComboBox.getValue();
            int vat = Integer.valueOf(vatTextField.getText());
            int active = Math.abs(tabPane.getSelectionModel().getSelectedIndex() - 1); //because activeitems table has index 0 and number 1 in database means that item is active

            PreparedStatement stmt = ConnectionData.conn.prepareStatement("SELECT FoodCategoryId From FoodCategory WHERE name = ?");
            stmt.setString(1, foodCategoryComboBox.getValue());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int foodCategoryId = rs.getInt("FoodCategoryId");
            rs.close();

            stmt = ConnectionData.conn.prepareStatement("INSERT INTO FoodItem " +
                    "VALUES (NULL, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, name); stmt.setInt(2, price);
            stmt.setInt(3, foodCategoryId); stmt.setString(4, foodCategory);
            stmt.setInt(5, vat); stmt.setInt(6, active);
            stmt.executeUpdate();
            stmt.close();

            FoodItemList.readFoodItems();
            activeFoodItemsTable.setItems(FoodItemList.activeFoodItems);
            unactiveFoodItemsTable.setItems(FoodItemList.unactiveFoodItems);
            activeFoodItemsTable.refresh();
            unactiveFoodItemsTable.refresh();
            clear();
        }
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
    void initialize() throws SQLException{
        modifyButton.setDisable(true);
        activeButton.setDisable(true);

        List<TableView<FoodItem>> tableViews = new ArrayList<TableView<FoodItem>>();
        tableViews.add(activeFoodItemsTable); tableViews.add(unactiveFoodItemsTable);

        for (TableView<FoodItem> t : tableViews) {
            t.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("FoodId"));
            t.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("Name"));
            t.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("Price"));
            t.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("foodCategory"));
            t.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("vat"));
        }

        FoodItemList.readFoodItems();
        activeFoodItemsTable.setItems(FoodItemList.activeFoodItems);
        unactiveFoodItemsTable.setItems(FoodItemList.unactiveFoodItems);

        FoodCategoriesPanelController ctrl = new FoodCategoriesPanelController();
        ctrl.refreshFoodCategories(foodCategoryComboBox);

        Utils.forceDecimals(vatTextField);
        Utils.forceDecimals(priceTextField);
    }

    private boolean checkData() {

        boolean dataFlag = true;

        if (nameTextField.getText().isEmpty()) {
            dataFlag = false;
            Alerts.showErrorAlert("Name field is empty!");
        }

        if (priceTextField.getText().isEmpty()) {
            dataFlag = false;
            Alerts.showErrorAlert("Price field is empty!");
        }

        if (foodCategoryComboBox.getValue().isEmpty()) {
            dataFlag = false;
            Alerts.showErrorAlert("You didn't choose food category!");
        }

        if (vatTextField.getText().isEmpty()) {
            dataFlag = false;
            Alerts.showErrorAlert("VAT field is empty!");
        }

        return dataFlag;
    }

    private void clear() {
        nameTextField.clear();
        priceTextField.clear();
        foodCategoryComboBox.setValue(null);
        vatTextField.clear();
    }
}
