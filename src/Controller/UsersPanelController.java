package Controller;

import Model.UserData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class UsersPanelController {
    Connection conn=null;


    @FXML
    private Label loggedUserLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField surnameTextField;

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
    private Button backButton;

    public void setDBConnection(Connection conn){
        this.conn=conn;
    }

    @FXML
    void modifyUser(ActionEvent event) {
        int phone;
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String surname = surnameTextField.getText();
        String function = functionComboBox.getValue();
        String position = "not defined";
        try{
            phone = Integer.valueOf(phoneTextField.getText());
        }
        catch (NumberFormatException e){}

        /*
        UPDATE IN DATABASE, also
        we must check if values are correct (atm i don't know if we have to do it in java
        or database will send proper information when we will try INSERT wrong values)
         */


    }

    @FXML
    void registerNewUser(ActionEvent event) {
        int phone;
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String surname = surnameTextField.getText();
        String function = functionComboBox.getValue();
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
    void showMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));
        Parent mainMenu = (Parent) loader.load();
        MainMenuController controller = loader.<MainMenuController>getController();
        controller.setDBConnection(conn);

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    public void initialize(){
        loggedUserLabel.setText("Logged user: " + UserData.login);
        functionComboBox.getItems().add("employee");
        functionComboBox.getItems().add("manager");

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
