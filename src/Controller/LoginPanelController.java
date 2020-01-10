package Controller;

import Model.ConnectionData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Model.UserData;

//import javax.jws.soap.SOAPBinding;

public class LoginPanelController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void login() throws IOException, SQLException {

        /*
        here should be login and password authorization
        with database
         */

        /*//choiceDialog for debug purposes
        List<String> choices = new ArrayList<>();
        choices.add("manager");
        choices.add("employee");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("manager", choices);
        dialog.setTitle("Choice function // for debug");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose your function:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            function = result.get();
        }*/

        if (verifyUser()) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));
            Parent parent = (Parent) loader.load();
            MainMenuController controller = loader.<MainMenuController>getController();

            Scene oldScene = stage.getScene();
            stage.setScene(new Scene(parent, oldScene.getWidth(), oldScene.getHeight()));
        }
        else {
            passwordTextField.setText(null);
            Alerts.showErrorAlert("Wrong login or password!");
        }


    }

    //login verification
    private boolean verifyUser() throws SQLException {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        PreparedStatement stmt = ConnectionData.conn.prepareStatement("SELECT COUNT(*) Over() as ccc, UserId, Login, Function " +
                "FROM Users WHERE Login = ? AND Password = ?");
        stmt.setString(1, login);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println(rs.getInt(1));
            if (rs.getInt(1) == 1) {
                UserData.id = rs.getInt("UserId");
                UserData.login = rs.getString("Login");
                UserData.function = rs.getString("Function");
                rs.close();
                stmt.close();
                return true;
            }
        }
        return false;
    }
}
