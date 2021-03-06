package Main;

import Controller.LoginPanelController;
import Controller.MainMenuController;
import Model.*;
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
    public void start(Stage primaryStage) throws Exception {

        ConnectionData.conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "sys as sysdba");
        connectionProps.put("password", "Oracle123");
        try {
            ConnectionData.conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1524/" +
                    "XE", connectionProps);
            System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            try {
                connectionProps.put("user", "ot");
                connectionProps.put("password", "Orcl1234");
                ConnectionData.conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/"+"pdborcl", connectionProps);
                System.out.println("Połączono z bazą danych");

            } catch (SQLException ex2) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE,
                        "nie udało się połączyć z bazą danych", ex2);
                System.exit(-1);
            }
        }


        UserList.readUsers();
        StockItemList.readItems();
        SupplierList.readSuppliers();

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
