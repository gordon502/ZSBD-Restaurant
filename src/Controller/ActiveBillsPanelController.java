package Controller;

import Model.ConnectionData;
import Model.Order;
import Model.OrderList;
import Model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import oracle.jdbc.proxy.annotation.Pre;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActiveBillsPanelController {

    private Integer chosenOrderId;

    private String chosenOrderLogin;

    @FXML
    private TableView<Order> activeOrdersTable;

    @FXML
    private Button finalizeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private Button showButton;

    @FXML
    void deleteOrder(ActionEvent event) {

    }

    @FXML
    void finalizeOrder(ActionEvent event) throws SQLException {
        if (checkAccessibility()) {
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("UPDATE Orders SET " +
                    "finalized = 1 WHERE OrderId = ?");
            stmt.setInt(1, chosenOrderId);
            stmt.executeUpdate();

            refresh();
        }
    }

    @FXML
    void showMainMenu(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/MainMenuScene.fxml");
    }

    @FXML
    void showOrder(ActionEvent event) {

    }

    @FXML
    void initialize() throws SQLException {
        OrderList.readOrders();

        activeOrdersTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("OrderId"));
        activeOrdersTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("Login"));
        activeOrdersTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("Date"));
        activeOrdersTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("Amount"));

        activeOrdersTable.setItems(OrderList.orders);

        showButton.setDisable(true);
        finalizeButton.setDisable(true);
        deleteButton.setDisable(true);

        activeOrdersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showButton.setDisable(false);
                finalizeButton.setDisable(false);
                deleteButton.setDisable(false);
                chosenOrderId = newValue.getOrderId();
                chosenOrderLogin = newValue.getLogin();
            }
        });
    }

    //check if user have permission to do anything with bill
    private boolean checkAccessibility() {
        if (UserData.function.equals("manager") || UserData.login.equals(chosenOrderLogin)) {
            return true;
        }
        else {
            Alerts.showErrorAlert("You don't own this bill!");
            return false;
        }

    }

    private void refresh() throws SQLException{
        OrderList.readOrders();
        showButton.setDisable(true);
        finalizeButton.setDisable(true);
        deleteButton.setDisable(true);
        chosenOrderId = null;
        chosenOrderLogin = null;

        activeOrdersTable.setItems(OrderList.orders);
    }


}
