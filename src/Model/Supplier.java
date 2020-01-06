package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private SimpleIntegerProperty supplierId;
    private SimpleStringProperty name;
    private SimpleIntegerProperty phoneNumber;
    private SimpleStringProperty email;

    public Supplier(Integer supplierId, String name, Integer phoneNumber, String email) {
        this.supplierId = new SimpleIntegerProperty(supplierId);
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleIntegerProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public String getName() {
        return name.get();
    }

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public SimpleIntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public SimpleIntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }
}
