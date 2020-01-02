package Controller;

import Model.ConnectionData;
import Model.User;
import Model.UserData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class UsersPanelController {
    private ObservableList<User> users;

    @FXML
    private TableView<User> userTable;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private ComboBox<String> functionComboBox;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<String> postitionCombo;

    @FXML
    private TextField rateTextField;

    @FXML
    private Button registerButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button fireButton;

    @FXML
    private Button backButton;

    @FXML
    void modifyUser(ActionEvent event) {
        int phone;
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String function = functionComboBox.getValue();
        String position = "not defined";
        try {
            phone = Integer.valueOf(phoneTextField.getText());
        } catch (NumberFormatException e) {
        }

        /*
        UPDATE IN DATABASE, also
        we must check if values are correct (atm i don't know if we have to do it in java
        or database will send proper information when we will try INSERT wrong values)
         */


    }

    @FXML
    void registerNewUser(ActionEvent event) throws SQLException {
        int phone = 0;
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String function = functionComboBox.getValue();
        String position = "not defined";
        Float hourRate = Float.valueOf(rateTextField.getText());

        try {
            phone = Integer.valueOf(phoneTextField.getText());
        } catch (NumberFormatException e) {
        }

        CallableStatement stmt = ConnectionData.conn.prepareCall("{call ADD_USER(?,?,?,?,?,?,?,?)}");
        stmt.setString(1, login);
        stmt.setString(2, password);
        stmt.setString(3, function);
        stmt.setString(4, firstName);
        stmt.setString(5, lastName);
        if (phone != 0) {
            stmt.setInt(6, phone);
        } else {
            stmt.setNull(6, Types.INTEGER);
        }

        stmt.setString(7, position);
        stmt.setFloat(8, hourRate);
        stmt.execute();
        stmt.close();

        /*
        INSERT TO DATABASE, also
        we must check if values are correct (atm i don't know if we have to do it in java
        or database will send proper information when we will try INSERT wrong values)
         */


    }

    @FXML
    void showMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));
        Parent mainMenu = (Parent) loader.load();
        MainMenuController controller = loader.<MainMenuController>getController();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    void fireUser(ActionEvent event) {

    }

    @FXML
    public void initialize() throws SQLException {
        loggedUserLabel.setText("Logged user: " + UserData.login);
        functionComboBox.getItems().add("manager");
        functionComboBox.getItems().add("employee");

        //force phoneTextField to get only numeric values
        phoneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //read users from db
        ArrayList temp = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM users");
        while (rs.next()) {
            temp.add(new User(rs.getInt("UserId"), rs.getString("Login"),
                    rs.getString("Password"), rs.getString("Function")
                    , rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Position"), rs.getFloat("HourlyRate"), rs.getInt("PhoneNumber")));
        }
        rs.close();
        stmt.close();

        users=FXCollections.observableArrayList(temp);

        userTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("userId"));
        userTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("login"));
        userTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("firstName"));
        userTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("lastName"));
        userTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("position"));
        userTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("hourRate"));
        userTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("phone"));
        userTable.setItems(users);

    }
}
