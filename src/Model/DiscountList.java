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
        ResultSet rs = stmt.executeQuery("SELECT DiscountId, DiscountCode, FoodCategoryId, Name, DiscountValue FROM Discount NATURAL INNER JOIN FoodCategory");
        while (rs.next()) {
            temp.add(new Discount(rs.getInt("DiscountId"), rs.getString("DiscountCode"), rs.getInt("FoodCategoryId"), rs.getString("name"), rs.getInt("DiscountValue")));
        }
        rs.close();
        stmt.close();
        discounts = FXCollections.observableArrayList(temp);
    }
}
