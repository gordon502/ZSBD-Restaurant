package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SupplierList {
    public static ObservableList<Supplier> suppliers;

    public static void readSuppliers() throws SQLException {
        ArrayList temp = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM suppliers");
        while (rs.next()) {
            temp.add(new Supplier(rs.getInt("SupplierId"), rs.getString("Name"), rs.getInt("PhoneNumber"), rs.getString("EmailAddress")));
        }
        rs.close();
        stmt.close();
        suppliers = FXCollections.observableArrayList(temp);
    }
}
