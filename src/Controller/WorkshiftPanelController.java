package Controller;

import Model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WorkshiftPanelController {
    ArrayList time;
    ObservableList<WorkshiftItem> workshiftItems;
    WorkshiftItem selectedWorkshift;

    public class WorkshiftId {
        public int userid;
        public String date;

        public WorkshiftId(int userid, String date) {
            this.userid = userid;
            this.date = date;
        }

        @Override
        public int hashCode() {
            return (userid + date).hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this.userid == ((WorkshiftId) o).userid && this.date.equals(((WorkshiftId) o).date);
        }
    }

    Map<WorkshiftId, WorkshiftItem> workshiftMap = new HashMap<WorkshiftId, WorkshiftItem>();


    @FXML
    private ComboBox<String> userCombo;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button genButton;

    @FXML
    private Button countButton;

    @FXML
    private ComboBox<String> endCombo;

    @FXML
    private ComboBox<String> startCombo;

    @FXML
    private DatePicker selDate;

    @FXML
    private Button addmodButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<WorkshiftItem> workshiftTable;

    @FXML
    void addmod(ActionEvent event) throws SQLException {
        if (!Arrays.asList(userCombo.getSelectionModel().getSelectedIndex(), startCombo.getSelectionModel().getSelectedIndex(), endCombo.getSelectionModel().getSelectedIndex()).contains(-1) && selDate.getValue() != null) {
            WorkshiftId workshiftId = new WorkshiftId(Integer.valueOf(userCombo.getSelectionModel().getSelectedItem().split(" ")[0]), selDate.getValue().toString());
            WorkshiftItem workshift = workshiftMap.get(workshiftId);
            if (workshiftMap.get(workshiftId) != null) {
                workshift.setStart(startCombo.getSelectionModel().getSelectedItem());
                workshift.setEnd(endCombo.getSelectionModel().getSelectedItem());

                PreparedStatement stmt = ConnectionData.conn.prepareStatement("update WorkShift set starttime=?, endtime=?, hours=? WHERE UserId=? AND dateofws=?");
                stmt.setString(1, workshift.getStart());
                stmt.setString(2, workshift.getEnd());
                stmt.setInt(3, workshift.countHours());
                stmt.setInt(4, Integer.valueOf(userCombo.getSelectionModel().getSelectedItem().split(" ")[0]));
                stmt.setDate(5, Date.valueOf(selDate.getValue()));
                stmt.executeUpdate();
                stmt.close();
            } else {
                WorkshiftItem workshiftItem = new WorkshiftItem(UserList.usersMap.get(workshiftId.userid).getUserId(),
                        selDate.getValue().toString(), startCombo.getSelectionModel().getSelectedItem(), endCombo.getSelectionModel().getSelectedItem(), (endCombo.getSelectionModel().getSelectedIndex() - startCombo.getSelectionModel().getSelectedIndex()));
                workshiftItems.add(workshiftItem);
                workshiftMap.put(workshiftId, workshiftItem);

                PreparedStatement stmt = ConnectionData.conn.prepareStatement("insert into WorkShift values(null, ?, ?, ?, ?, ?)");
                stmt.setInt(1, workshiftId.userid);
                stmt.setDate(2, Date.valueOf(selDate.getValue()));
                stmt.setString(3, workshiftItem.getStart());
                stmt.setString(4, workshiftItem.getEnd());
                stmt.setInt(5, workshiftItem.countHours());
                stmt.executeUpdate();
                stmt.close();
            }
        }
    }

    @FXML
    void countHours(ActionEvent event) throws SQLException {
        if (userCombo.getSelectionModel().getSelectedIndex() != -1 && startDate.getValue() != null && endDate.getValue() != null) {
            CallableStatement stmt = ConnectionData.conn.prepareCall("{? = call EMPLOYEEHOURS(?, ?, ?)}");
            stmt.setInt(2, Integer.valueOf(userCombo.getSelectionModel().getSelectedItem().split(" ")[0]));
            stmt.setDate(3, Date.valueOf(startDate.getValue()));
            stmt.setDate(4, Date.valueOf(endDate.getValue()));
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.execute();
            int userhours = stmt.getInt(1);
            stmt.close();

            System.out.println(userhours); //dziala, zrobic alert
        }
    }

    @FXML
    void removeWorkshift(ActionEvent event) throws SQLException {
        if (selectedWorkshift != null) {
            WorkshiftId workshiftId = new WorkshiftId(selectedWorkshift.getUserid(), selectedWorkshift.getDate());
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("delete from WorkShift where userid=? and dateofws=?");
            stmt.setInt(1, selectedWorkshift.getUserid());
            stmt.setDate(2, Date.valueOf(LocalDate.parse(selectedWorkshift.getDate())));
            stmt.executeUpdate();
            stmt.close();
            workshiftItems.remove(selectedWorkshift);
            workshiftMap.remove(workshiftId);
            selectedWorkshift = null;
        }
    }

    @FXML
    void scheduleGenerate(ActionEvent event) throws SQLException {
        if (userCombo.getSelectionModel().getSelectedIndex() != -1 && startDate.getValue() != null && endDate.getValue() != null) {
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("delete from workshift where userid=? AND dateofws>=? AND dateofws<=?");
            stmt.setInt(1, Integer.valueOf(userCombo.getSelectionModel().getSelectedItem().split(" ")[0]));
            stmt.setDate(2, Date.valueOf(startDate.getValue()));
            stmt.setDate(3, Date.valueOf(endDate.getValue()));
            int changes = stmt.executeUpdate();
            stmt.close();

            stmt = ConnectionData.conn.prepareStatement("insert into workshift (userid, dateofws, starttime, endtime, hours)\n" +
                    "select userid, daydate, starttime, endtime, hours\n" +
                    "from schedule where userid=? AND daydate>=? AND daydate<=?");
            stmt.setInt(1, Integer.valueOf(userCombo.getSelectionModel().getSelectedItem().split(" ")[0]));
            stmt.setDate(2, Date.valueOf(startDate.getValue()));
            stmt.setDate(3, Date.valueOf(endDate.getValue()));
            changes = stmt.executeUpdate();
            stmt.close();
            readUserWorkshifts();
        }
    }

    @FXML
    void showMainMenu(ActionEvent event) throws IOException {
        SceneSwitcher ss = new SceneSwitcher();
        ss.switchScene(backButton, "../View/MainMenuScene.fxml");

    }

    public void readUserWorkshifts() throws SQLException {
        workshiftItems.clear();
        workshiftMap.clear();
        Integer readUserId = Integer.valueOf(userCombo.getSelectionModel().getSelectedItem().split(" ")[0]);

        PreparedStatement stmt = ConnectionData.conn.prepareStatement("SELECT * FROM WorkShift WHERE UserId=?");
        stmt.setInt(1, readUserId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            WorkshiftItem workshiftItem = new WorkshiftItem(UserList.usersMap.get(readUserId).getUserId(),
                    rs.getDate("DateOfWS").toLocalDate().toString(), rs.getString("StartTime"), rs.getString("EndTime"), rs.getInt("HOURS"));
            workshiftItems.add(workshiftItem);
            workshiftMap.put(new WorkshiftId(readUserId, rs.getDate("DateOfWS").toLocalDate().toString()), workshiftItem);
        }
        rs.close();
        stmt.close();
    }


    @FXML
    public void initialize() {
        ArrayList<WorkshiftItem> temp = new ArrayList();
        workshiftItems = FXCollections.observableArrayList(temp);

        workshiftTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("user"));
        workshiftTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("date"));
        workshiftTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("start"));
        workshiftTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("end"));
        workshiftTable.setItems(workshiftItems);

        time = new ArrayList();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                time.add("0" + String.valueOf(i) + ":00");
            } else {
                time.add(String.valueOf(i) + ":00");
            }
        }
        startCombo.getItems().addAll(time);
        time.add("24:00");

        endCombo.setDisable(true);
        startCombo.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                endCombo.getItems().clear();
                endCombo.setDisable(false);
                for (int i = newValue.intValue() + 1; i < time.size(); i++) {
                    endCombo.getItems().add(time.get(i).toString());
                }

            }
        });

        for (User user : UserList.users) {
            userCombo.getItems().add(user.getUser());
        }

        startDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today.minusDays(1)) > 0);
            }
        });


        endDate.setDisable(true);

        startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            endDate.setDisable(false);
            endDate.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(startDate.getValue()) < 0 || date.compareTo(today.minusDays(1)) > 0);
                }
            });
        });

        selDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0);
            }
        });

        workshiftTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                removeButton.setDisable(false);
                selectedWorkshift = workshiftTable.getSelectionModel().getSelectedItem();
                startCombo.getSelectionModel().select(selectedWorkshift.getStart());
                endCombo.getSelectionModel().select(selectedWorkshift.getEnd());
                selDate.setValue(LocalDate.parse(selectedWorkshift.getDate()));
            }
            else {
                removeButton.setDisable(true);
            }
        });

        userCombo.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    readUserWorkshifts();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        genButton.disableProperty().bind(startDate.valueProperty().isNull().or(endDate.valueProperty().isNull().or(userCombo.valueProperty().isNull())));
        countButton.disableProperty().bind(startDate.valueProperty().isNull().or(endDate.valueProperty().isNull().or(userCombo.valueProperty().isNull())));
        addmodButton.disableProperty().bind(userCombo.valueProperty().isNull().or(selDate.valueProperty().isNull().or(startCombo.valueProperty().isNull().or(endCombo.valueProperty().isNull()))));
//        removeButton.disableProperty().bind(userCombo.valueProperty().isNull().or(selDate.valueProperty().isNull()));
        removeButton.setDisable(true);
    }
}
