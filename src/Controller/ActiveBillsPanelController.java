package Controller;

import Model.Order;
import Model.OrderList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import oracle.jdbc.proxy.annotation.Pre;

import java.io.IOException;
import java.sql.SQLException;

public class ActiveBillsPanelController {

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
    void finalizeOrder(ActionEvent event) {

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
    }

}
