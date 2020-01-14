package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import oracle.jdbc.proxy.annotation.Pre;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SelectedBillPanelController {

    private Integer chosenOrderItemId;

    @FXML
    private GridPane dataGrid;

    @FXML
    private TextField quantityTextField;

    @FXML
    private ComboBox<String> foodCategoryComboBox;

    @FXML
    private ComboBox<String> nameComboBox;

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
    private TableView<OrderItem> orderItemTable;

    @FXML
    void addOrderItem(ActionEvent event) throws SQLException{
        if (checkData()) {
            int foodId = 0;
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("INSERT INTO OrderItem " +
                    "VALUES(NULL, ?, ?, ?)");
            foodId = getFoodIdFromComboBox();
            stmt.setInt(1, UserData.orderId);
            stmt.setInt(2, foodId);
            stmt.setInt(3, Integer.valueOf(quantityTextField.getText()));
            stmt.executeUpdate();
            stmt.close();

            updateBillAmount();

            OrderItemList.readOrderItems(UserData.orderId);
            refresh();
            clear();
        }
    }

    @FXML
    void deleteOrderItem(ActionEvent event) throws SQLException{
        PreparedStatement stmt = ConnectionData.conn.prepareStatement("DELETE FROM OrderItem " +
                "WHERE OrderItemId = ?");
        stmt.setInt(1, chosenOrderItemId);
        stmt.executeUpdate();
        stmt.close();

        updateBillAmount();

        clear();
        refresh();
    }

    @FXML
    void modifyOrderItem(ActionEvent event) throws SQLException{
        if (checkData()) {
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("UPDATE OrderItem " +
                    "SET FoodId = ?, Quantity = ? WHERE OrderItemId = ?");
            int foodId = getFoodIdFromComboBox();
            stmt.setInt(1, foodId);
            stmt.setInt(2, Integer.valueOf(quantityTextField.getText()));
            stmt.setInt(3, chosenOrderItemId);
            stmt.executeUpdate();
            stmt.close();

            updateBillAmount();

            refresh();
        }
    }

    @FXML
    void showActiveBillsPanel(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/ActiveBillsPanelScene.fxml");
    }

    @FXML
    void initialize() throws SQLException {
        Utils.forceDecimals(quantityTextField);
        nameComboBox.setDisable(true);

        OrderItemList.readOrderItems(UserData.orderId);
        FoodItemList.readFoodItems();

        foodCategoryComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            if (newValue != null) {
                fillNameComboBox(newValue);
                nameComboBox.setDisable(false);
            }
        });

        orderItemTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillOrderItemData(newValue);
            }
        } );

        fillFoodCategoryComboBox();

        orderItemTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("FoodId"));
        orderItemTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("Name"));
        orderItemTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("FoodCategory"));
        orderItemTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("Quantity"));
        orderItemTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("Price"));
        orderItemTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("vat"));

        orderItemTable.setItems(OrderItemList.orderItems);
    }


    private void fillFoodCategoryComboBox() throws SQLException{
        FoodCategoriesPanelController fc = new FoodCategoriesPanelController();
        fc.refreshFoodCategories(foodCategoryComboBox);
    }

    private void fillNameComboBox(String foodCategory) {
        nameComboBox.getItems().clear();
        for (FoodItem fi : FoodItemList.activeFoodItems) {
            if (fi.getFoodCategory().equals(foodCategory)) {
                nameComboBox.getItems().add(fi.getName());
            }
        }
    }

    private boolean checkData() {
        boolean dataFlag = true;

        if (foodCategoryComboBox.getValue().isEmpty()) {
            dataFlag = false;
            Alerts.showErrorAlert("You didn't choose food category!");
        }

        if (nameComboBox.getValue().isEmpty()) {
            dataFlag = false;
            Alerts.showErrorAlert("You didn't choose food!");
        }

        if (quantityTextField.getText().isEmpty()) {
            dataFlag = false;
            Alerts.showErrorAlert("Quantity field is empty!");
        }

        return dataFlag;
    }

    private void fillOrderItemData(OrderItem orderItem) {
        chosenOrderItemId = orderItem.getOrderItemId();
        foodCategoryComboBox.setValue(orderItem.getFoodCategory());
        quantityTextField.setText(String.valueOf(orderItem.getQuantity()));
        nameComboBox.setValue(orderItem.getName());
        nameComboBox.setDisable(false);
        modifyButton.setDisable(false);
        deleteButton.setDisable(false);
    }

    private void clear() {
        chosenOrderItemId = null;
        foodCategoryComboBox.setValue(null);
        nameComboBox.setValue(null);
        nameComboBox.setDisable(true);
        quantityTextField.clear();
        modifyButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void refresh() throws SQLException{
        OrderItemList.readOrderItems(UserData.orderId);
        orderItemTable.setItems(OrderItemList.orderItems);
        orderItemTable.refresh();
    }

    private int getFoodIdFromComboBox() {
        for (FoodItem fi : FoodItemList.activeFoodItems){
            if (fi.getName().equals(nameComboBox.getValue())) {
                return fi.getFoodId();
            }
        }
        return -1;
    }

    //update amount in bill
    private void updateBillAmount() throws SQLException{
        PreparedStatement stmt = ConnectionData.conn.prepareStatement("UPDATE Orders SET Amount = " +
                "(select sum(quantity * (price + (price * vat / 100))) from orderitem join fooditem Using (foodid) where orderid = ?) where OrderId = ?");
        stmt.setInt(1, UserData.orderId);
        stmt.setInt(2, UserData.orderId);
        stmt.executeUpdate();
        stmt.close();
    }
}

