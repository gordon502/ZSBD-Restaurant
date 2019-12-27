package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /*
         here should be initialized connection between application
         and oracle database
         */

        /*
        also we need to pass connection variable to controller or make it global
         */

        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginPanelScene.fxml"));
        primaryStage.setTitle("ZSBD Restaurant");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
