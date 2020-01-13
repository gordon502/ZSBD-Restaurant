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

    public static void showInformationAlert(String confirmMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText(confirmMessage);

        alert.showAndWait();
    }

    public static void showHoursAlert(String title, String startdate, String enddate, int hours) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText("Godziny w okresie " + startdate + " - " + enddate +": " + hours);

        alert.showAndWait();
    }
}
