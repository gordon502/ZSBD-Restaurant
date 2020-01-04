package Main;

import Controller.LoginPanelController;
import Controller.MainMenuController;
import Model.ConnectionData;
import Model.UserList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        ConnectionData.conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "ot");
        connectionProps.put("password", "Orcl1234");
        try {
            ConnectionData.conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/"+
            "pdborcl", connectionProps);
            System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,
                    "nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        }

        UserList.readUsers();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/LoginPanelScene.fxml"));
        Parent root = (Parent) loader.load();
        LoginPanelController controller = loader.<LoginPanelController>getController();

        primaryStage.setTitle("ZSBD Restaurant");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
