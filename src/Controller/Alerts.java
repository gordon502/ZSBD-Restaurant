package Controller;

import javafx.scene.control.Alert;

public class Alerts {

    public static void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();

    }
}