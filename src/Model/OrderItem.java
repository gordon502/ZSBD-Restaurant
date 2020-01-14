package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderItem {
    private SimpleIntegerProperty orderItemId;
    private SimpleIntegerProperty orderId;
    private SimpleIntegerProperty foodId;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty name;
    private SimpleIntegerProperty price;
    private SimpleStringProperty foodCategory;
    private SimpleIntegerProperty vat;

    public OrderItem(int orderItemId, int orderId, int foodId, int quantity, String name, int price, String foodCategory, int vat) {
        this.orderItemId = new SimpleIntegerProperty(orderItemId);
        this.orderId = new SimpleIntegerProperty(orderId);
        this.foodId = new SimpleIntegerProperty(foodId);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(price);
        this.foodCategory = new SimpleStringProperty(foodCategory);
        this.vat = new SimpleIntegerProperty(vat);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public String getFoodCategory() {
        return foodCategory.get();
    }

    public String getName() {
        return name.get();
    }

    public int getFoodId() {
        return foodId.get();
    }

    public int getPrice() {
        return price.get();
    }

    public int getOrderItemId() {
        return orderItemId.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory.set(foodCategory);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setFoodId(int foodId) {
        this.foodId.set(foodId);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setVat(int vat) {
        this.vat.set(vat);
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId.set(orderItemId);
    }

    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public SimpleIntegerProperty vatProperty() {
        return vat;
    }

    public SimpleStringProperty foodCategoryProperty() {
        return foodCategory;
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleIntegerProperty foodIdProperty() {
        return foodId;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public SimpleIntegerProperty orderItemIdProperty() {
        return orderItemId;
    }
}
