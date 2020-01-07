package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TableView<StockItem> stockItemTable;

    @FXML
    void addProduct(ActionEvent event) throws SQLException {



        //if data are correct
        if (checkData()) {
            String name = nameTextField.getText();
            Integer quantity = Integer.valueOf(quantityTextField.getText());
            Integer demand = Integer.valueOf(demandTextField.getText());
            Integer price = Integer.valueOf(priceTextField.getText());

            String strvalue = supplierComboBox.getValue();
            Integer supplierId = Integer.valueOf(strvalue.substring(0, strvalue.indexOf(".")));
            String supplierName = "";
            Integer supplierPhone = 0;
            String supplierEmail = "";

            //reading correct supplier data
            for (Supplier sup : SupplierList.suppliers){
                if (sup.getSupplierId() == supplierId){
                    supplierName = sup.getName();
                    supplierPhone = sup.getPhoneNumber();
                    supplierEmail = sup.getEmail();
                    break;
                }
            }

            PreparedStatement stmt = ConnectionData.conn.prepareStatement("INSERT INTO Product(ProductId, Name, Price, SupplierId)" +
                    "VALUES (NULL, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, price);
            stmt.setInt(3, supplierId);
            stmt.executeUpdate();

            Statement select_stmt = ConnectionData.conn.createStatement();
            ResultSet rs = select_stmt.executeQuery("SELECT ProductId FROM product order by ProductId desc fetch first 1 rows only");
            rs.next();
            Integer productId = rs.getInt("ProductId");
            
            stmt = ConnectionData.conn.prepareStatement("INSERT INTO STOCKROOM " +
                    "VALUES (NULL, ?, ?, ?, ?, ?)");
            stmt.setInt(1, UserData.id); stmt.setInt(2, productId); stmt.setString(3, name);
            stmt.setInt(4, quantity); stmt.setInt(5, demand);
            stmt.executeUpdate();
            
            select_stmt = ConnectionData.conn.createStatement();
            rs = select_stmt.executeQuery("SELECT StockRoomId From StockRoom Order by StockRoomId DESC FETCH FIRST 1 ROWS ONLY");
            rs.next();
            Integer stockId = rs.getInt("StockRoomId");
           
            rs.close();
            select_stmt.close();
            stmt.close();
            
            StockItemList.stockItems.add(new StockItem(stockId, UserData.id, productId, name, quantity, demand, price, supplierId, supplierName, supplierPhone, supplierEmail));
        }
        else {
            showErrorAlert();
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
    void showSuppliers(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/SuppliersPanelScene.fxml"));
        Parent mainMenu = (Parent) loader.load();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void initialize() {
        modifyButton.setDisable(false);

        stockItemTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("StockId"));
        stockItemTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("productName"));
        stockItemTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("Quantity"));
        stockItemTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("Demand"));
        stockItemTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("Price"));
        stockItemTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("supplierId"));
        stockItemTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("supplierName"));
        stockItemTable.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("PhoneNumber"));
        stockItemTable.getColumns().get(8).setCellValueFactory(new PropertyValueFactory("EmailAddress"));


        stockItemTable.setItems(StockItemList.stockItems);
        

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
        priceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });



        //fill combobox with suppliers
        for (Supplier sup : SupplierList.suppliers) {
            supplierComboBox.getItems().add(sup.getSupplierId() + ". " + sup.getName());
        }

    }

    private boolean checkData() {
        boolean dataFlag = true;

        try { Integer.valueOf(demandTextField.getText()); }
        catch (NumberFormatException e) { dataFlag = false; }

        try { Integer.valueOf(quantityTextField.getText()); }
        catch (NumberFormatException e) { dataFlag = false; }

        try { Integer.valueOf(priceTextField.getText()); }
        catch (NumberFormatException e) { dataFlag = false; }

        String supp = supplierComboBox.getValue();
        if (supp.indexOf(".") == -1) { dataFlag = false; }

        if (!nameTextField.getText().equals("")) {  }
        else { dataFlag = false; }

        return dataFlag;
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText("Entered data is incorrect!");

        alert.showAndWait();
    }

}
