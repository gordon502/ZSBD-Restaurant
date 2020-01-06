package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StockItem {
    private SimpleIntegerProperty stockId;
    private SimpleIntegerProperty userId;
    private SimpleIntegerProperty productId;
    private SimpleStringProperty name;
    private SimpleIntegerProperty quantity;
    private SimpleIntegerProperty demand;

    public StockItem(Integer stockId, Integer userId, Integer productId, String name, Integer quantity, Integer demand) {
        this.stockId = new SimpleIntegerProperty(stockId);
        this.userId = new SimpleIntegerProperty(userId);
        this.productId = new SimpleIntegerProperty(productId);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.demand = new SimpleIntegerProperty(demand);
    }

    public int getUserId() {
        return userId.get();
    }

    public String getName() {
        return name.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public int getDemand() {
        return demand.get();
    }

    public int getStockId() {
        return stockId.get();
    }

    public int getProductId() {
        return productId.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setDemand(int demand) {
        this.demand.set(demand);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public SimpleIntegerProperty getStockIdProperty() {
        return stockId;
    }

    public SimpleIntegerProperty getUserIdProperty() {
        return userId;
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public SimpleIntegerProperty getQuantityProperty() {
        return quantity;
    }

    public SimpleIntegerProperty getDemandProperty() {
        return demand;
    }

    public SimpleIntegerProperty getProductIdProperty() {
        return productId;
    }

}
