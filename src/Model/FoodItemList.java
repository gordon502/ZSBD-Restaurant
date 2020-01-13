package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodItemList {
    public static ObservableList<FoodItem> activeFoodItems;
    public static ObservableList<FoodItem> unactiveFoodItems;

    public static void readFoodItems() throws SQLException {
        ArrayList tempactive = new ArrayList();
        ArrayList tempunactive = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM FoodItem f JOIN Discount d ON d.FoodCategoryId = f.FoodCategoryId WHERE active = 1");
        while (rs.next()){
            tempactive.add(new FoodItem(rs.getInt("FoodId"), rs.getString("Name"), rs.getInt("Price"), rs.getInt("FoodCategoryId"), rs.getString("FoodCategory"),
                    rs.getInt("VAT"), rs.getInt("DiscountId"), rs.getString("DiscountCode"), rs.getInt("DiscountValue"), rs.getInt("active")));
        }

        rs = stmt.executeQuery("SELECT * FROM FoodItem f JOIN Discount d ON d.FoodCategoryId = f.FoodCategoryId WHERE active = 0");
        while (rs.next()) {
            tempunactive.add(new FoodItem(rs.getInt("FoodId"), rs.getString("Name"), rs.getInt("Price"), rs.getInt("FoodCategoryId"), rs.getString("FoodCategory"),
                rs.getInt("VAT"), rs.getInt("DiscountId"), rs.getString("DiscountCode"), rs.getInt("DiscountValue"), rs.getInt("active")));

        }
        rs.close();
        stmt.close();
        activeFoodItems = FXCollections.observableArrayList(tempactive);
        unactiveFoodItems = FXCollections.observableArrayList(tempunactive);
    }
}
