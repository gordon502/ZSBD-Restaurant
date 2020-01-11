package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class WorkshiftItem {
    private SimpleIntegerProperty user;
    private SimpleStringProperty date;
    private SimpleStringProperty start;
    private SimpleStringProperty end;

    public WorkshiftItem(SimpleIntegerProperty user, SimpleStringProperty date, SimpleStringProperty start, SimpleStringProperty end) {
        this.user = user;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public int getUser() {
        return user.get();
    }

    public SimpleIntegerProperty userProperty() {
        return user;
    }

    public void setUser(int user) {
        this.user.set(user);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public String getEnd() {
        return end.get();
    }

    public SimpleStringProperty endProperty() {
        return end;
    }

    public void setEnd(String end) {
        this.end.set(end);
    }
}
