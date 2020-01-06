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
                "SELECT st.StockRoomId, st.UserId, st.ProductId, st.Name, st.Quantity, st.Demand, p.Price, sup.SupplierId, sup.Name, sup.PhoneNumber, sup.EmailAddress " +
                        "FROM stockroom st JOIN product p ON p.ProductId = st.ProductId JOIN Supplier sup ON sup.SupplierId = p.SupplierId");
        while (rs.next()) {
            temp.add(new StockItem(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6),
                                   rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getInt(10), rs.getString(11)));
        }
        rs.close();
        stmt.close();
        stockItems = FXCollections.observableArrayList(temp);
    }
}
