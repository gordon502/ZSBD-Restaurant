package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class Order {
    private SimpleIntegerProperty orderId;
    private SimpleIntegerProperty userId;
    private SimpleStringProperty login;
    private SimpleObjectProperty<Date> date;
    private SimpleIntegerProperty amount;

    public Order(int orderId, int userId, String login, Date date, int amount) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.userId = new SimpleIntegerProperty(userId);
        this.login = new SimpleStringProperty(login);
        this.date = new SimpleObjectProperty<Date>(date);
        this.amount = new SimpleIntegerProperty(amount);
    }

    public int getAmount() {
        return amount.get();
    }

    public int getOrderId() {
        return orderId.get();
    }

    public int getUserId() {
        return userId.get();
    }

    public Date getDate() {
        return date.get();
    }

    public String getLogin() {
        return login.get();
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public SimpleObjectProperty<Date> dateProperty() {
        return date;
    }
}
