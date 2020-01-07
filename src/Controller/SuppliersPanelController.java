package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class SuppliersPanelController {

    private int chosenSupplierId;

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
    private Button backButton;

    @FXML
    private TableView<Supplier> supplierTable;


    @FXML
    void addSupplier(ActionEvent event) throws SQLException {
        if (checkData()) {
            String name = nameTextField.getText();
            Integer phone = Integer.valueOf(phoneTextField.getText());
            String email = emailTextField.getText();
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("INSERT INTO supplier(SupplierId, name, phoneNumber, emailaddress)" +
                    "VALUES (NULL, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, phone);
            stmt.setString(3, email);

            Statement select_stmt = ConnectionData.conn.createStatement();
            int changes = stmt.executeUpdate();
            System.out.println("dodano");
            stmt.close();

            ResultSet rs = select_stmt.executeQuery("SELECT SupplierId FROM supplier order by supplierid desc fetch first 1 rows only");
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("SupplierId");
            }

            if (id > 0) {
                SupplierList.suppliers.add(new Supplier(id, name, phone, email));
            }

            clear();
        }
        else {
            showErrorAlert();
        }

    }

    @FXML
    void modifySupplier(ActionEvent event) throws SQLException{
        if (checkData()) {
            String name = nameTextField.getText();
            Integer phone = Integer.valueOf(phoneTextField.getText());
            String email = emailTextField.getText();
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("UPDATE supplier SET " +
                    "Name = ?, PhoneNumber = ?, EmailAddress = ? WHERE SupplierId = ?");
            stmt.setString(1, name); stmt.setInt(2, phone);
            stmt.setString(3, email); stmt.setInt(4, chosenSupplierId);
            stmt.executeUpdate();
            stmt.close();

            Supplier modifiedSupplier = supplierTable.getSelectionModel().getSelectedItem();
            modifiedSupplier.setPhoneNumber(phone);
            modifiedSupplier.setEmail(email);
            modifiedSupplier.setName(name);
            //supplierTable.setItems(SupplierList.suppliers);
        }
        else {
            showErrorAlert();
        }

    }

    @FXML
    void showStockRoomPanel(ActionEvent event) throws IOException {
        Stage stage = (Stage) addButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/StockRoomPanelScene.fxml"));
        Parent stockRoom = (Parent) loader.load();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(stockRoom, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    void initialize() {
        modifyButton.setDisable(true);

        supplierTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("SupplierId"));
        supplierTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("Name"));
        supplierTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        supplierTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("email"));

        supplierTable.setItems(SupplierList.suppliers);

        supplierTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillSupplierData(newValue);
//                registerButton.setDisable(true);
                modifyButton.setDisable(false);
            }
        });


    }

    public void fillSupplierData(Supplier supplier) {
        chosenSupplierId = supplier.getSupplierId();
        nameTextField.setText(supplier.getName());
        phoneTextField.setText(String.valueOf(supplier.getPhoneNumber()));
        emailTextField.setText(supplier.getEmail());
    }

    private boolean checkData() {
        boolean dataFlag = true;
        if (nameTextField.getText().equals("")) { dataFlag = false; }

        try { Integer.valueOf(phoneTextField.getText()); }
        catch (NumberFormatException e) { dataFlag = false; }

        if (emailTextField.getText().equals("")) { dataFlag = false; }

        return dataFlag;
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText("Entered data is incorrect!");

        alert.showAndWait();
    }

    private void clear() {
        nameTextField.setText(null);
        phoneTextField.setText(null);
        emailTextField.setText(null);
        modifyButton.setDisable(true);
    }



}
