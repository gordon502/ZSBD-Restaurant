package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StockItemList {
    public static ObservableList<StockItem> stockItems;

    public static void readItems() throws SQLException {
        ArrayList temp = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM stockroom");
        while (rs.next()) {
            temp.add(new StockItem(rs.getInt("StockId"), rs.getInt("UserId"),
                    rs.getInt("ProductId"), rs.getString("Name")
                    , rs.getInt("Quantity"), rs.getInt("Demand")));
        }
        rs.close();
        stmt.close();
        stockItems = FXCollections.observableArrayList(temp);
    }
}
