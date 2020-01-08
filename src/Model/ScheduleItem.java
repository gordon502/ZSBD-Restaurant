package Model;

import javafx.beans.property.SimpleStringProperty;

public class ScheduleItem {
    private SimpleStringProperty user;
    private SimpleStringProperty monday;
    private SimpleStringProperty tuesday;
    private SimpleStringProperty wednesday;
    private SimpleStringProperty thursday;
    private SimpleStringProperty friday;
    private SimpleStringProperty saturday;
    private SimpleStringProperty sunday;

    public ScheduleItem(String user) {
        this.user = new SimpleStringProperty(user);
        monday = new SimpleStringProperty();
        tuesday = new SimpleStringProperty();
        wednesday = new SimpleStringProperty();
        thursday = new SimpleStringProperty();
        friday = new SimpleStringProperty();
        saturday = new SimpleStringProperty();
        sunday = new SimpleStringProperty();
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

    public String getMonday() {
        return monday.get();
    }

    public SimpleStringProperty mondayProperty() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday.set(monday);
    }

    public String getTuesday() {
        return tuesday.get();
    }

    public SimpleStringProperty tuesdayProperty() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday.set(tuesday);
    }

    public String getWednesday() {
        return wednesday.get();
    }

    public SimpleStringProperty wednesdayProperty() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday.set(wednesday);
    }

    public String getThursday() {
        return thursday.get();
    }

    public SimpleStringProperty thursdayProperty() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday.set(thursday);
    }

    public String getFriday() {
        return friday.get();
    }

    public SimpleStringProperty fridayProperty() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday.set(friday);
    }

    public String getSaturday() {
        return saturday.get();
    }

    public SimpleStringProperty saturdayProperty() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday.set(saturday);
    }

    public String getSunday() {
        return sunday.get();
    }

    public SimpleStringProperty sundayProperty() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday.set(sunday);
    }

    interface setAction {
        void set(String string);
    }

    private setAction[] setActions = new setAction[]{
            new setAction() {
                public void set(String string) {
                    setMonday(string);
                }
            },
            new setAction() {
                public void set(String string) {
                    setTuesday(string);
                }
            },
            new setAction() {
                public void set(String string) {
                    setWednesday(string);
                }
            },
            new setAction() {
                public void set(String string) {
                    setThursday(string);
                }
            },
            new setAction() {
                public void set(String string) {
                    setFriday(string);
                }
            },
            new setAction() {
                public void set(String string) {
                    setSaturday(string);
                }
            },
            new setAction() {
                public void set(String string) {
                    setSunday(string);
                }
            },
    };

    public void set(int index, String string) {
        setActions[index].set(string);
    }

}
