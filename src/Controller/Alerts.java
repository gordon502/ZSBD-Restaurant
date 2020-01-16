package Controller;

import Model.ConnectionData;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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

    public static void showHoursAlert(String title, String startdate, String enddate, int hours, float pay) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        BigDecimal bd = new BigDecimal(pay).setScale(2, RoundingMode.HALF_UP);
        alert.setContentText("Godziny w okresie " + startdate + " - " + enddate + ": " + hours+"\nDo zapłaty: "+ bd);

        alert.showAndWait();
    }

    public static boolean showConfirmationAlert(String warningMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING!");
        alert.setHeaderText("DANGEROUS OPERATION!");
        alert.setContentText(warningMessage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    public static void showRaportAlert(Date startDate, Date endDate) throws SQLException {
        int clientNumbers = 0;
        int totalMoney = 0;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Raport generated!");
        alert.setHeaderText("Statistics from " + startDate.toString() + " to " + endDate.toString());

        PreparedStatement stmt = ConnectionData.conn.prepareStatement("SELECT COUNT(*) OVER() FROM Orders WHERE finalized = 1 AND DateOfOrder >= ? AND DateOfOrder <= ?");
        stmt.setDate(1, startDate);
        stmt.setDate(2, endDate);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            clientNumbers = rs.getInt(1);
        }

        stmt = ConnectionData.conn.prepareStatement("SELECT SUM(Amount) FROM Orders WHERE finalized = 1 AND DateOfOrder >= ? AND DateOfOrder <= ?");
        stmt.setDate(1, startDate);
        stmt.setDate(2, endDate);
        rs = stmt.executeQuery();
        while (rs.next()){
            totalMoney = rs.getInt(1);
        }

        rs.close();
        stmt.close();

        alert.setContentText("Number of clients: " + String.valueOf(clientNumbers) + "\nTotal money earned: " + totalMoney);
        alert.showAndWait();
    }
}
