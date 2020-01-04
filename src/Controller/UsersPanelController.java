package Controller;

import Model.ConnectionData;
import Model.User;
import Model.UserData;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class UsersPanelController {
    private ObservableList<User> users;
    private Integer chosenUserId = null;

    @FXML
    private TableView<User> userTable;

    @FXML
    private Label userLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordField;

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
    private GridPane dataGrid;

    @FXML
    void modifyUser(ActionEvent event) throws SQLException {
        if (chosenUserId != null) {
            int phone = 0;
            String login = loginTextField.getText();
            String password = passwordField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String function = functionComboBox.getValue();
            String position = postitionCombo.getValue();

            Float hourRate = Float.valueOf(rateTextField.getText());

            try {
                phone = Integer.valueOf(phoneTextField.getText());
            } catch (NumberFormatException e) {
            }

            PreparedStatement stmt = ConnectionData.conn.prepareStatement("update users " +
                    "set login=?" +
                    ",password=?" +
                    ",function=?" +
                    ",firstname=?" +
                    ",lastname=?" +
                    ",jobposition=?" +
                    ",hourlyrate=?" +
                    ",phonenumber=?" +
                    " where userid=?"
            );
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, function);
            stmt.setString(4, firstName);
            stmt.setString(5, lastName);
            stmt.setString(6, position);
            stmt.setFloat(7, hourRate);

            if (phone != 0) {
                stmt.setInt(8, phone);
            } else {
                stmt.setNull(8, Types.INTEGER);
            }
            stmt.setInt(9, chosenUserId);

            stmt.execute();
            stmt.close();
            User modifiedUser = userTable.getSelectionModel().getSelectedItem();
            modifiedUser.setLogin(login);
            modifiedUser.setPassword(password);
            modifiedUser.setFirstName(firstName);
            modifiedUser.setLastName(lastName);
            modifiedUser.setFunction(function);
            modifiedUser.setPosition(position);
            modifiedUser.setHourRate(hourRate);
            modifiedUser.setPhone(phone);
        }
    }

    @FXML
    void registerNewUser(ActionEvent event) throws SQLException {
        int phone = 0;
        String login = loginTextField.getText();
        String password = passwordField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String function = functionComboBox.getValue();
        String position = postitionCombo.getValue();

        Float hourRate = Float.valueOf(rateTextField.getText());

        try {
            phone = Integer.valueOf(phoneTextField.getText());
        } catch (NumberFormatException e) {
        }

        CallableStatement stmt = ConnectionData.conn.prepareCall("{call ADD_USER(?,?,?,?,?,?,?,?, ?)}");
        stmt.setString(1, login);
        stmt.setString(2, password);
        stmt.setString(3, function);
        stmt.setString(4, firstName);
        stmt.setString(5, lastName);
        stmt.setString(6, position);
        stmt.setFloat(7, hourRate);
        if (phone != 0) {
            stmt.setInt(8, phone);
        } else {
            stmt.setNull(8, Types.INTEGER);
        }
        stmt.registerOutParameter(9, Types.INTEGER);

        stmt.execute();
        int userid = stmt.getInt(9);
        users.add(new User(userid, login, password, function, firstName, lastName, position, hourRate, phone, 0));

        stmt.close();


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
    void fireUser(ActionEvent event) throws SQLException {
        if (chosenUserId != null) {
            PreparedStatement stmt = ConnectionData.conn.prepareStatement(
                    "update users set fired=1 where userid=?");
            stmt.setInt(1, chosenUserId);
            int changes = stmt.executeUpdate();
            System.out.println("Zmodyfikowano " + changes + " krotek.");
            stmt.close();
            User firedUser = userTable.getSelectionModel().getSelectedItem();
            users.remove(firedUser);
            firedUser.setFired(1);
        }
    }

    @FXML
    void clear(ActionEvent event) {
        chosenUserId = null;
        loginTextField.clear();
        passwordField.clear();
        firstNameTextField.clear();
        lastNameTextField.clear();
        phoneTextField.clear();
        rateTextField.clear();
        functionComboBox.getSelectionModel().select(null);
        postitionCombo.getSelectionModel().select(null);
        userTable.getSelectionModel().select(null);
        userLabel.setText("New user");
//        registerButton.setDisable(false);
        modifyButton.setDisable(true);
        fireButton.setDisable(true);
    }

    public void fillUserData(User user) {
        chosenUserId = user.getUserId();
        loginTextField.setText(user.getLogin());
        passwordField.setText(user.getPassword());
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        phoneTextField.setText(String.valueOf(user.getPhone()));
        rateTextField.setText(String.valueOf(user.getHourRate()));
        functionComboBox.getSelectionModel().select(user.getFunction());
        postitionCombo.getSelectionModel().select(user.getPosition());
        userLabel.setText(String.valueOf(user.getUserId()) + " - " + user.getFirstName() + " " + user.getLastName());
    }

    public void readUsers() throws SQLException {
        ArrayList temp = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM users where fired=0");
        while (rs.next()) {
            temp.add(new User(rs.getInt("UserId"), rs.getString("Login"),
                    rs.getString("Password"), rs.getString("Function")
                    , rs.getString("FirstName"), rs.getString("LastName"), rs.getString("JobPosition"), rs.getFloat("HourlyRate"), rs.getInt("PhoneNumber"), rs.getInt("Fired")));
        }
        rs.close();
        stmt.close();
        users = FXCollections.observableArrayList(temp);
    }

    @FXML
    public void initialize() throws SQLException {
        modifyButton.setDisable(true);
        fireButton.setDisable(true);

        userLabel.setText("New user");
        functionComboBox.getItems().add("manager");
        functionComboBox.getItems().add("employee");

        postitionCombo.getItems().add("manager");
        postitionCombo.getItems().add("cook");
        postitionCombo.getItems().add("cleaner");
        postitionCombo.getItems().add("waiter");

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

        rateTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[\\d\\.]*")) {
                    rateTextField.setText(newValue.replaceAll("[^[\\d\\.]]", ""));
                }
            }
        });

        //read users from db

        userTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("userId"));
        userTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("login"));
        userTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("firstName"));
        userTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("lastName"));
        userTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("position"));
        userTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("hourRate"));
        userTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("phone"));

        readUsers();
        userTable.setItems(users);

        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillUserData(newValue);
//                registerButton.setDisable(true);
                modifyButton.setDisable(false);
                fireButton.setDisable(false);
            }
        });
    }
}
