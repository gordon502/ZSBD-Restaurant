package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DiscountList {

    public static ObservableList<Discount> discounts;

    public static void readDiscounts() throws SQLException {
        ArrayList temp = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Discount");
        while (rs.next()) {
            temp.add(new Discount(rs.getInt("DiscountId"), rs.getString("DiscountCode"), rs.getString("FoodCategory"), rs.getInt("DiscountValue")));
        }
        rs.close();
        stmt.close();
        discounts = FXCollections.observableArrayList(temp);
    }
}
