package Controller;

import Model.UserData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class NewUserPanelController {

    Connection conn = null;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<String> functionsComboBox;

    public void setDBConnection(Connection conn){
        this.conn=conn;
    }

    @FXML
    public void showUsersDataPanel() throws IOException {
        Stage stage = (Stage) loggedUserLabel.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/UsersPanelScene.fxml"));
        Parent usersPanel = (Parent) loader.load();
        UsersPanelController controller = loader.<UsersPanelController>getController();
        controller.setDBConnection(conn);

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(usersPanel, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void registerNewUser(){
        int phone;
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String surname = surnameTextField.getText();
        String function = functionsComboBox.getValue();
        String position = "not defined";
        try{
            phone = Integer.valueOf(phoneTextField.getText());
        }
        catch (NumberFormatException e){}

        /*
        INSERT TO DATABASE, also
        we must check if values are correct (atm i don't know if we have to do it in java
        or database will send proper information when we will try INSERT wrong values)
         */


    }

    @FXML
    public void initialize(){
        loggedUserLabel.setText("Logged user: " + UserData.login);
        functionsComboBox.getItems().add("employee");
        functionsComboBox.getItems().add("manager");

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
    }
}
