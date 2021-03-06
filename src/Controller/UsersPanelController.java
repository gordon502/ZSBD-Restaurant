package Controller;

import Model.ConnectionData;
import Model.User;
import Model.UserData;
import Model.UserList;
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


import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class UsersPanelController {
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
    private TableView<User> firedTable;

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

    public boolean containsLogin(final ObservableList<User> list, final String login) {
        return list.stream().filter(o -> o.getLogin().equals(login)).findFirst().isPresent();
    }

    public boolean containsPhone(final ObservableList<User> list, final int phone) {
        return list.stream().filter(o -> o.getPhone() == phone).findFirst().isPresent();
    }

    @FXML
    void registerNewUser(ActionEvent event) throws SQLException {
        try{
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

            boolean error = false;
            String errorMessage = "";

            if (containsLogin(UserList.users, login)) {
                errorMessage += "There is already a user with this login!";
                error = true;
            }
            if (containsPhone(UserList.users, phone)) {
                errorMessage += "\nThere is already a user with this phone number!";
                error = true;
            }
            if (error) {
                Alerts.showErrorAlert(errorMessage);
                return;
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
            User newUser = new User(userid, login, password, function, firstName, lastName, position, hourRate, phone, 0);
            UserList.users.add(newUser);
            UserList.usersMap.put(newUser.getUserId(), newUser);

            stmt.close();
        }
        catch(Exception e){
            Alerts.showErrorAlert("Problem!");
        }
    }

    @FXML
    void showMainMenu(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/MainMenuScene.fxml");
    }

    @FXML
    void fireUser(ActionEvent event) throws SQLException {
        if (chosenUserId != null) {
            PreparedStatement stmt = ConnectionData.conn.prepareStatement(
                    "update users set fired=1 where userid=?");
            stmt.setInt(1, chosenUserId);
            int changes = stmt.executeUpdate();
            stmt.close();
            User firedUser = userTable.getSelectionModel().getSelectedItem();
            UserList.firedUsers.add(firedUser);
            UserList.users.remove(firedUser);
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

    @FXML
    public void initialize() {
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
                if (!newValue.matches("(\\d+(\\.(\\d{1,2})?)?$)?")) {
                    rateTextField.setText(oldValue);
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

//        readUsers();
        userTable.setItems(UserList.users);

        firedTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("userId"));
        firedTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("login"));
        firedTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("firstName"));
        firedTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("lastName"));
        firedTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("position"));
        firedTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("hourRate"));
        firedTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("phone"));

        firedTable.setItems(UserList.firedUsers);


        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillUserData(newValue);
                modifyButton.setDisable(false);
                fireButton.setDisable(false);
                firedTable.getSelectionModel().select(null);
            }
        });

        firedTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                clear(new ActionEvent());
            }
        });

        registerButton.disableProperty().bind(loginTextField.textProperty().isNull().or(
                passwordField.textProperty().isNull().or(
                        functionComboBox.valueProperty().isNull().or(
                                firstNameTextField.textProperty().isNull().or(
                                        lastNameTextField.textProperty().isNull().or(
                                                postitionCombo.valueProperty().isNull().or(
                                                        rateTextField.textProperty().isEmpty().or(
                                                                phoneTextField.textProperty().isEmpty()
                                                        )
                                                ))
                                )
                        )
                )
        ));
    }
}
