package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FoodItem extends Discount{
    private SimpleIntegerProperty foodId;
    private SimpleStringProperty name;
    private SimpleIntegerProperty price;
    private SimpleStringProperty foodCategory;
    private SimpleIntegerProperty vat;
    private SimpleIntegerProperty discountId;
    private SimpleStringProperty discountCode;
    private SimpleIntegerProperty value;

    public FoodItem(int foodId, String name, int price, String foodCategory, int vat, int discountId, String discountCode, int value) {
        super(discountId, discountCode, foodCategory, value);
        this.foodId = new SimpleIntegerProperty(foodId);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(price);
        this.foodCategory = new SimpleStringProperty(foodCategory);
        this.vat = new SimpleIntegerProperty(vat);
    }

    public int getFoodId() {
        return foodId.get();
    }

    public String getName() {
        return name.get();
    }

    public int getPrice() {
        return price.get();
    }

    public String getFoodCategory() {
        return foodCategory.get();
    }

    public int getVat() {
        return vat.get();
    }


    public SimpleIntegerProperty foodIdProperty() {
        return foodId;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public SimpleStringProperty foodCategoryProperty() {
        return foodCategory;
    }

    public SimpleIntegerProperty vatProperty() {
        return vat;
    }

    public void setFoodId(int foodId) {
        this.foodId.set(foodId);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory.set(foodCategory);
    }

    public void setVat(int vat) {
        this.vat.set(vat);
    }


}
