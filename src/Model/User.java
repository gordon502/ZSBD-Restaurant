package Model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleIntegerProperty userId;
    private SimpleStringProperty login;
    private SimpleStringProperty password;
    private SimpleStringProperty function;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty position;
    private SimpleFloatProperty hourRate;
    private SimpleIntegerProperty phone;
    private SimpleIntegerProperty fired;

    public int getFired() {
        return fired.get();
    }

    public SimpleIntegerProperty firedProperty() {
        return fired;
    }

    public void setFired(int fired) {
        this.fired.set(fired);
    }

    public User(Integer userId, String login, String password, String function, String fName, String lName, String position, Float hourRate, Integer phone, Integer fired) {
        this.userId = new SimpleIntegerProperty(userId);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.function = new SimpleStringProperty(function);
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.position = new SimpleStringProperty(position);
        this.hourRate = new SimpleFloatProperty(hourRate);
        this.phone = new SimpleIntegerProperty(phone);
        this.fired = new SimpleIntegerProperty(fired);
    }

    public String getUser() {
        return String.valueOf(userId.get()) + " - " + getFirstName() + " " + getLastName();
    }

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getFunction() {
        return function.get();
    }

    public SimpleStringProperty functionProperty() {
        return function;
    }

    public void setFunction(String function) {
        this.function.set(function);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public float getHourRate() {
        return hourRate.get();
    }

    public SimpleFloatProperty hourRateProperty() {
        return hourRate;
    }

    public void setHourRate(float hourRate) {
        this.hourRate.set(hourRate);
    }

    public int getPhone() {
        return phone.get();
    }

    public SimpleIntegerProperty phoneProperty() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone.set(phone);
    }
}