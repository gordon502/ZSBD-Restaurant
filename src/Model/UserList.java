package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserList {
    public static ObservableList<User> users;
    public static ObservableList<User> firedUsers;

    public static void readUsers() throws SQLException {
        ArrayList temp = new ArrayList();
        ArrayList fireTemp = new ArrayList();
        Statement stmt = ConnectionData.conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM users");
        while (rs.next()) {
            User user = new User(rs.getInt("UserId"), rs.getString("Login"),
                    rs.getString("Password"), rs.getString("Function"),
                    rs.getString("FirstName"), rs.getString("LastName"),
                    rs.getString("JobPosition"), rs.getFloat("HourlyRate"),
                    rs.getInt("PhoneNumber"), rs.getInt("Fired"));
            if (user.getFired() == 1) {fireTemp.add(user);}
            else {temp.add(user);}
        }
        rs.close();
        stmt.close();
        users = FXCollections.observableArrayList(temp);
        firedUsers=FXCollections.observableArrayList(fireTemp);
    }
}
