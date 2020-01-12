package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodItemList {
    public static ObservableList<FoodItem> foodItems;

    public static void readFoodItems() throws SQLException {
        ArrayList temp = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM FoodItem f JOIN Discount d ON d.FoodCategory = f.FoodCategory");
        while (rs.next()){
            temp.add(new FoodItem(rs.getInt("FoodId"), rs.getString("Name"), rs.getInt("Price"), rs.getInt("FoodCategoryId"), rs.getString("FoodCategory"),
                    rs.getInt("VAT"), rs.getInt("DiscountId"), rs.getString("DiscountCode"), rs.getInt("DiscountValue")));
        }
        rs.close();
        stmt.close();
        foodItems = FXCollections.observableArrayList(temp);
    }
}
