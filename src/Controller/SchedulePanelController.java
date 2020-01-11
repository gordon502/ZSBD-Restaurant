package Controller;

import Model.ConnectionData;
import Model.ScheduleItem;
import Model.User;
import Model.UserList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SchedulePanelController {
    ObservableList<ScheduleItem> scheduleItems;
    ArrayList time;
    Calendar now;
    String currentWeek;

    @FXML
    private Label weekLabel;

    @FXML
    private Button prevWeekButton;

    @FXML
    private Button nextWeekButton;

    @FXML
    private ComboBox<String> userCombo;

    @FXML
    private ComboBox<String> dayCombo;

    @FXML
    private ComboBox<String> startCombo;

    @FXML
    private ComboBox<String> endCombo;

    @FXML
    private Button addmodButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<ScheduleItem> scheduleTable;

    @FXML
    void addmod(ActionEvent event) throws SQLException {

        if (!Arrays.asList(userCombo.getSelectionModel().getSelectedIndex(), dayCombo.getSelectionModel().getSelectedIndex(), startCombo.getSelectionModel().getSelectedIndex(), endCombo.getSelectionModel().getSelectedIndex()).contains(-1)) {
            String user = userCombo.getSelectionModel().getSelectedItem();
            int day = dayCombo.getSelectionModel().getSelectedIndex();
            String start = startCombo.getSelectionModel().getSelectedItem();
            String end = endCombo.getSelectionModel().getSelectedItem();

            ScheduleItem scheduleItem = scheduleItems.get(userCombo.getSelectionModel().getSelectedIndex());
            scheduleItem.set(dayCombo.getSelectionModel().getSelectedIndex(), start + " - " + end);

            CallableStatement stmt = ConnectionData.conn.prepareCall("{call ADD_SCHEDULE(?,?,?,?,?,?, ?)}");

            stmt.setString(1, user.split(" ")[0]);
            stmt.setDate(2, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(now.getTime())));
            now.add(Calendar.DAY_OF_YEAR, day);
            stmt.setDate(3, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(now.getTime())));
            now.add(Calendar.DAY_OF_YEAR, -day);
            stmt.setInt(4, day);
            stmt.setString(5, start);
            stmt.setString(6, end);
            stmt.setInt(7, Integer.valueOf(end.split(":")[0])-Integer.valueOf(start.split(":")[0]));

            stmt.execute();
            stmt.close();
        }
    }

    public void clearSchedule(){
        for (ScheduleItem scheduleItem : scheduleItems){
            scheduleItem.reset();
        }
        scheduleTable.setItems(scheduleItems);
    }

    @FXML
    void removeSchedule(ActionEvent event) throws SQLException {
        if (!Arrays.asList(userCombo.getSelectionModel().getSelectedIndex(), dayCombo.getSelectionModel()).contains(-1)) {
            now.add(Calendar.DAY_OF_YEAR, dayCombo.getSelectionModel().getSelectedIndex());
            PreparedStatement stmt = ConnectionData.conn.prepareStatement("delete from schedule where UserId=? and DayDate=?");
            stmt.setInt(1, Integer.valueOf(userCombo.getSelectionModel().getSelectedItem().split(" ")[0]));
            stmt.setDate(2, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(now.getTime())));
            now.add(Calendar.DAY_OF_YEAR, -dayCombo.getSelectionModel().getSelectedIndex());
            stmt.executeUpdate();
            stmt.close();

            ScheduleItem scheduleItem = scheduleItems.get(userCombo.getSelectionModel().getSelectedIndex());
            scheduleItem.set(dayCombo.getSelectionModel().getSelectedIndex(), null);
        }

    }

    @FXML
    void nextWeek(ActionEvent event) throws SQLException {
        now.add(Calendar.DAY_OF_YEAR, 7);
        updateLabels();
        setupSchedule();
    }

    @FXML
    void prevWeek(ActionEvent event) throws SQLException {
        now.add(Calendar.DAY_OF_YEAR, -7);
        updateLabels();
        setupSchedule();
    }

    public void updateLabels() {
        currentWeek = new SimpleDateFormat("dd.MM.yyyy").format(now.getTime());
        weekLabel.setText(new SimpleDateFormat("dd.MM.yyyy").format(now.getTime()));

        scheduleTable.getColumns().get(1).setText("Mon.\n" + new SimpleDateFormat("dd.MM").format(now.getTime()));
        now.add(Calendar.DAY_OF_YEAR, 1);
        scheduleTable.getColumns().get(2).setText("Tue.\n" + new SimpleDateFormat("dd.MM").format(now.getTime()));
        now.add(Calendar.DAY_OF_YEAR, 1);
        scheduleTable.getColumns().get(3).setText("Wed.\n" + new SimpleDateFormat("dd.MM").format(now.getTime()));
        now.add(Calendar.DAY_OF_YEAR, 1);
        scheduleTable.getColumns().get(4).setText("Thu.\n" + new SimpleDateFormat("dd.MM").format(now.getTime()));
        now.add(Calendar.DAY_OF_YEAR, 1);
        scheduleTable.getColumns().get(5).setText("Fri.\n" + new SimpleDateFormat("dd.MM").format(now.getTime()));
        now.add(Calendar.DAY_OF_YEAR, 1);
        scheduleTable.getColumns().get(6).setText("Sat.\n" + new SimpleDateFormat("dd.MM").format(now.getTime()));
        now.add(Calendar.DAY_OF_YEAR, 1);
        scheduleTable.getColumns().get(7).setText("Sun.\n" + new SimpleDateFormat("dd.MM").format(now.getTime()));
        now.add(Calendar.DAY_OF_YEAR, -6);
    }

    @FXML
    void showMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenuScene.fxml"));
        Parent mainMenu = (Parent) loader.load();
        MainMenuController controller = loader.<MainMenuController>getController();

        Scene oldScene = stage.getScene();
        stage.setScene(new Scene(mainMenu, oldScene.getWidth(), oldScene.getHeight()));
    }

    public void setupSchedule() throws SQLException {
        clearSchedule();
        PreparedStatement stmt = ConnectionData.conn.prepareStatement("SELECT userid, weekday, starttime, endtime FROM schedule where WeekDate=?");
        stmt.setDate(1, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(now.getTime())));
        ResultSet rs=stmt.executeQuery();
        while (rs.next()) {
            int userid=rs.getInt("userid");
            for(ScheduleItem scheduleItem : scheduleItems){
                if(Integer.valueOf(scheduleItem.getUser().split(" ")[0])==userid){
                    scheduleItem.set(rs.getInt("weekday"), rs.getString("starttime")+" - "+rs.getString("endtime"));
                    break;
                }
            }

        }
        rs.close();
        stmt.close();

    }

    @FXML
    public void initialize() throws SQLException {
        now = Calendar.getInstance();
        int weekday = now.get(Calendar.DAY_OF_WEEK);
        if (weekday != Calendar.MONDAY) {
            int days = Math.abs((Calendar.MONDAY - weekday) % 7);
            now.add(Calendar.DAY_OF_YEAR, -days);
        }
        updateLabels();


        ArrayList tempItems = new ArrayList();
        for (User user : UserList.users) {
            ScheduleItem scheduleItem = new ScheduleItem(String.valueOf(user.getUserId()) + " - " + user.getFirstName() + " " + user.getLastName());
            tempItems.add(scheduleItem);
            userCombo.getItems().add(scheduleItem.getUser());
        }
        scheduleItems = FXCollections.observableArrayList(tempItems);
        dayCombo.getItems().addAll(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

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

        scheduleTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("user"));
        scheduleTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("monday"));
        scheduleTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("tuesday"));
        scheduleTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("wednesday"));
        scheduleTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("thursday"));
        scheduleTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("friday"));
        scheduleTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("saturday"));
        scheduleTable.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("sunday"));
        scheduleTable.setItems(scheduleItems);

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
        setupSchedule();

        addmodButton.disableProperty().bind(userCombo.valueProperty().isNull().or(dayCombo.valueProperty().isNull().or(startCombo.valueProperty().isNull().or(endCombo.valueProperty().isNull()))));
        removeButton.disableProperty().bind(userCombo.valueProperty().isNull().or(dayCombo.valueProperty().isNull()));
    }
}
