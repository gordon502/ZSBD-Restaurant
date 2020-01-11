package Model;

import javafx.beans.property.SimpleStringProperty;

public class WorkshiftItem {
    private SimpleStringProperty user;
    private SimpleStringProperty date;
    private SimpleStringProperty start;
    private SimpleStringProperty end;
    private int hours;
    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public WorkshiftItem(String user) {
        this.user = new SimpleStringProperty(user);
    }

    public WorkshiftItem(int userid, String date, String start, String end, int hours) {
        this.userid=userid;
        this.user = new SimpleStringProperty(UserList.usersMap.get(userid).getUser());
        this.date = new SimpleStringProperty(date);
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
        this.hours = hours;
    }

    public int countHours(){
        return Integer.valueOf(this.getEnd().split(":")[0]) - Integer.valueOf(this.getStart().split(":")[0]);
    }

    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
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
