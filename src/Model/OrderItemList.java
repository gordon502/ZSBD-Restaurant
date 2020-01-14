package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemList {
    public static ObservableList<OrderItem> orderItems;

    public static void readOrderItems(int orderId) throws SQLException {
        ArrayList temp = new ArrayList();

        PreparedStatement stmt = ConnectionData.conn.prepareStatement("SELECT oi.OrderItemId, oi.FoodId, oi.Quantity, fi.name AS FIName, fi.price, fi.foodCategoryId, fi.vat, fc.name AS FCName " +
                "FROM OrderItem oi JOIN FoodItem fi on oi.FoodId = fi.FoodId JOIN FoodCategory fc ON fi.FoodCategoryId = fc.FoodCategoryId WHERE OrderId = ?");
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            temp.add(new OrderItem(rs.getInt("OrderItemId"), orderId, rs.getInt("FoodId"), rs.getInt("Quantity"), rs.getString("FIName"), rs.getInt("Price"),
                    rs.getString("FCName"), rs.getInt("VAT")));
        }
        rs.close();
        stmt.close();

        orderItems = FXCollections.observableArrayList(temp);
    }
}
