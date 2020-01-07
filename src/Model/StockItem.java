package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StockItem {
    private SimpleIntegerProperty stockId;
    private SimpleIntegerProperty userId;
    private SimpleIntegerProperty productId;
    private SimpleStringProperty productName;
    private SimpleIntegerProperty quantity;
    private SimpleIntegerProperty demand;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty supplierId;
    private SimpleStringProperty supplierName;
    private SimpleIntegerProperty phoneNumber;
    private SimpleStringProperty emailAddress;

    public StockItem(Integer stockId, Integer userId, Integer productId, String productName, Integer quantity, Integer demand,
                     Integer price, Integer supplierId, String supplierName, Integer phoneNumber, String emailAddress) {
        this.stockId = new SimpleIntegerProperty(stockId);
        this.userId = new SimpleIntegerProperty(userId);
        this.productId = new SimpleIntegerProperty(productId);
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.demand = new SimpleIntegerProperty(demand);
        this.price = new SimpleIntegerProperty(price);
        this.supplierId = new SimpleIntegerProperty(supplierId);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.phoneNumber = new SimpleIntegerProperty(phoneNumber);
        this.emailAddress = new SimpleStringProperty(emailAddress);
    }

    public int getUserId() {
        return userId.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public int getPrice() {
        return price.get();
    }

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getSupplierName() {
        return supplierName.get();
    }

    public String getEmailAddress() {
        return emailAddress.get();
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

    public void setPrice(int price) {
        this.price.set(price);
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }

    public void setDemand(int demand) {
        this.demand.set(demand);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public SimpleIntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public SimpleIntegerProperty demandProperty() {
        return demand;
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public SimpleIntegerProperty productIdProperty() {
        return productId;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public SimpleIntegerProperty stockIdProperty() {
        return stockId;
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public SimpleIntegerProperty getStockIdProperty() {
        return stockId;
    }

    public SimpleIntegerProperty getUserIdProperty() {
        return userId;
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
