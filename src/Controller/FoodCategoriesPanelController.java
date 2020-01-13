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

    private Integer chosenFoodCategoryId;

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
        if (checkData()) {
            try {
                PreparedStatement stmt = ConnectionData.conn.prepareStatement("INSERT INTO FoodCategory VALUES " +
                        "(NULL, ?)");
                stmt.setString(1, foodCategoryComboBox.getValue());
                stmt.executeUpdate();
                stmt.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Byla juz taka kategoria");
            }
            try {
                PreparedStatement stmt = ConnectionData.conn.prepareStatement("SELECT FoodCategoryId FROM FoodCategory WHERE " +
                        "Name = ?");
                stmt.setString(1, foodCategoryComboBox.getValue());
                ResultSet rs = stmt.executeQuery();
                rs.next();
                Integer foodCategoryId = rs.getInt("FoodCategoryId");
                rs.close();

                stmt = ConnectionData.conn.prepareStatement("INSERT INTO Discount VALUES " +
                        "(NULL, ?, ?, ?)");
                stmt.setString(1, discountCodeTextField.getText());
                stmt.setInt(2, foodCategoryId);
                stmt.setInt(3, Integer.valueOf(valueTextField.getText()));
                stmt.executeUpdate();
                stmt.close();

                DiscountList.readDiscounts();
                discountTable.setItems(DiscountList.discounts);
                discountTable.refresh();
                refreshFoodCategories(foodCategoryComboBox);

                stmt.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
                Alerts.showErrorAlert("Record with given values exists!");
            }
        }
    }

    @FXML
    void modifyFoodCategory(ActionEvent event) {
        if (checkData()) {
            try {
                String foodCategory = foodCategoryComboBox.getValue();
                String discountCode = discountCodeTextField.getText();
                Integer value = Integer.valueOf(valueTextField.getText());

                PreparedStatement stmt = ConnectionData.conn.prepareStatement("UPDATE FoodCategory SET " +
                        "Name = ? where FoodCategoryId = ?");
                stmt.setString(1, foodCategory);
                stmt.setInt(2, chosenFoodCategoryId);
                stmt.executeUpdate();

                stmt = ConnectionData.conn.prepareStatement("UPDATE Discount SET " +
                        "DiscountCode = ?, DiscountValue = ? WHERE DiscountId = ?");
                stmt.setString(1, discountCode);
                stmt.setInt(2, value);
                stmt.setInt(3, chosenDiscountId);

                stmt.executeUpdate();



                DiscountList.readDiscounts();
                discountTable.setItems(DiscountList.discounts);
                discountTable.refresh();
                refreshFoodCategories(foodCategoryComboBox);

                stmt.close();

                clear();
            }
            catch (SQLException e) {
                e.printStackTrace();
                Alerts.showErrorAlert("This category exists!");
            }
        }
    }

    @FXML
    void showEditItemPanel(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/EditItemPanelScene.fxml");
    }

    private boolean checkData() {
        boolean dataFlag = true;

        if (foodCategoryComboBox.getValue().isEmpty()) {
            Alerts.showErrorAlert("You didn't choose food category!");
            dataFlag = false;
        }

        if(discountCodeTextField.getText().isEmpty()) {
            Alerts.showErrorAlert("You left discount code field empty!");
            dataFlag = false;
        }

        try {
            Integer.valueOf(valueTextField.getText());
        }
        catch (NumberFormatException e) {
            Alerts.showErrorAlert("Value format must be number!");
            dataFlag = false;
        }

        return dataFlag;
    }

    private void clear() {
        chosenFoodCategoryId = null;
        chosenDiscountId = null;
        discountCodeTextField.clear();
        foodCategoryComboBox.setValue(null);
        valueTextField.clear();
    }

    public void refreshFoodCategories(ComboBox<String> foodCategoryComboBox) throws SQLException{
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

    private void fillDiscountData(Discount discount) {
        chosenFoodCategoryId = discount.getFoodCategoryId();
        chosenDiscountId = discount.getDiscountId();
        foodCategoryComboBox.setValue(discount.getFoodCategory());
        discountCodeTextField.setText(discount.getDiscountCode());
        valueTextField.setText(String.valueOf(discount.getValue()));
    }

    @FXML
    void initialize() throws SQLException {
        DiscountList.readDiscounts();
        modifyButton.setDisable(true);

        discountTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("discountId"));
        discountTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("foodCategoryId"));
        discountTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("foodCategory"));
        discountTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("DiscountCode"));
        discountTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("value"));

        discountTable.setItems(DiscountList.discounts);

        refreshFoodCategories(foodCategoryComboBox);

        discountTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillDiscountData(newValue);
                modifyButton.setDisable(false);
            }
        });
    }

}
