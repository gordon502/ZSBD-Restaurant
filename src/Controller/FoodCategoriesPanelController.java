package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodCategoriesPanelController {

    private Integer chosenDiscountId;

    @FXML
    private GridPane dataGrid;

    @FXML
    private TextField discountCodeTextField;

    @FXML
    private TextField valueTextField;

    @FXML
    private ComboBox<String> foodCategoryComboBox;

    @FXML
    private Label userLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Discount> discountTable;

    @FXML
    void addFoodCategory(ActionEvent event) {
        try {
            if (checkData()) {
                PreparedStatement stmt = ConnectionData.conn.prepareStatement("INSERT INTO FoodCategory VALUES " +
                        "(NULL, ?)");
                stmt.setString(1, foodCategoryComboBox.getValue());
                stmt.executeUpdate();

                stmt = ConnectionData.conn.prepareStatement("INSERT INTO Discount VALUES " +
                        "(NULL, ?, ?, ?)");
                stmt.setString(1, discountCodeTextField.getText());
                stmt.setString(2, foodCategoryComboBox.getValue());
                stmt.setInt(3, Integer.valueOf(valueTextField.getText()));
                stmt.executeUpdate();
                stmt.close();

                DiscountList.readDiscounts();
                discountTable.setItems(DiscountList.discounts);
                discountTable.refresh();
                refreshFoodCategories();

                clear();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            Alerts.showErrorAlert("Record with given values exists!");
        }
    }

    @FXML
    void modifyFoodCategory(ActionEvent event) {

    }

    @FXML
    void showEditItemPanel(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/EditItemPanelScene.fxml");
    }


    @FXML
    void initialize() throws SQLException {
        DiscountList.readDiscounts();
        modifyButton.setDisable(true);

        discountTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("discountId"));
        discountTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("foodCategory"));
        discountTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("DiscountCode"));
        discountTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("value"));

        discountTable.setItems(DiscountList.discounts);

        refreshFoodCategories();
    }

    private boolean checkData() {
        return true;
    }

    private void clear() {
        chosenDiscountId = null;
        discountCodeTextField.clear();
        foodCategoryComboBox.setValue(null);
        valueTextField.clear();
    }

    private void refreshFoodCategories() throws SQLException{
        ArrayList<String> temp = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM FoodCategory");
        while (rs.next()) {
            temp.add(String.valueOf(rs.getString(2)));
        }
        rs.close();
        stmt.close();

        foodCategoryComboBox.getItems().clear();
        for (String s : temp) {
            foodCategoryComboBox.getItems().add(s);
        }
    }

}
