package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Discount {
    protected SimpleIntegerProperty discountId;
    protected SimpleStringProperty discountCode;
    private SimpleStringProperty foodCategory;
    protected SimpleIntegerProperty value;

    public Discount(int discountId, String discountCode, String foodCategory, int value) {
        this.discountId = new SimpleIntegerProperty(discountId);
        this.discountCode = new SimpleStringProperty(discountCode);
        this.foodCategory = new SimpleStringProperty(foodCategory);
        this.value = new SimpleIntegerProperty(value);
    }

    public int getDiscountId() {
        return discountId.get();
    }

    public String getDiscountCode() {
        return discountCode.get();
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty discountIdProperty() {
        return discountId;
    }

    public SimpleStringProperty discountCodeProperty() {
        return discountCode;
    }

    public SimpleStringProperty foodCategoryProperty() { return foodCategory; }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }



    public void setDiscountId(int discountId) {
        this.discountId.set(discountId);
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode.set(discountCode);
    }

    public void setFoodCategory(String value) { this.foodCategory.set(value);}

    public void setValue(int value) {
        this.value.set(value);
    }
}
