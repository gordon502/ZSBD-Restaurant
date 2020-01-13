package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderList {
    public static ObservableList<Order> orders;

    public static void readOrders() throws SQLException {
        ArrayList temp = new ArrayList();

        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Orders WHERE finalized = 0");
        while(rs.next()) {
            temp.add(new Order(rs.getInt("OrderId"), rs.getInt("UserId"), rs.getString("Login"), rs.getDate("DateOfOrder"), rs.getInt("Amount")));
        }
        rs.close();
        stmt.close();

        orders = FXCollections.observableArrayList(temp);
    }
}
