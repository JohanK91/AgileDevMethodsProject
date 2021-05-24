package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.Task;
import com.mysql.cj.protocol.a.NativePacketPayload;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class driverController implements Initializable
{
    @FXML
    ListView<String> taskView;

    @FXML
    ListView<String> unassignedTaskView;

    @FXML
    Button beginDriveButton;

    @FXML
    Button addTaskButton;

    @FXML
    Button removeTaskButton;

    @FXML
    Button logoutButton;

    ArrayList<String> myUnassignedAddresses = new ArrayList<>();
    ArrayList<String> myAssignedAddresses = new ArrayList<>();

    ArrayList<Task> myTasks;
    ArrayList<Task> myUnassignedTasks;

    private void setupTasks()
    {
        DbBridge dbBridge = AppManager.getInstance().getDb();
        try
        {
            myTasks = dbBridge.getDriverTasksUndone(dbBridge.getUID(AppManager.getInstance().getUser()));
            myUnassignedTasks = dbBridge.getDriverTasksUndone(-1);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        for (Task task : myTasks)
        {
            myAssignedAddresses.add(task.getAddress());
        }
        for (Task task : myUnassignedTasks)
        {
            myUnassignedAddresses.add(task.getAddress());
        }

        taskView.setItems(FXCollections.observableArrayList(myAssignedAddresses));
        unassignedTaskView.setItems(FXCollections.observableArrayList(myUnassignedAddresses));
    }

    public void reload()
    {
        myUnassignedAddresses.clear();
        myAssignedAddresses.clear();
        setupTasks();

        beginDriveButton.setDisable(false);

        if (myAssignedAddresses.size() == 0)
        {
            beginDriveButton.setDisable(true);
        }

        removeTaskButton.setDisable(taskView.getSelectionModel().getSelectedIndex() == -1);
        addTaskButton.setDisable(unassignedTaskView.getSelectionModel().getSelectedIndex() == -1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        reload();
    }

    @FXML
    public void beginDrivePressed(ActionEvent actionEvent) throws IOException
    {
        AppManager.getInstance().switchView("Views/driverDriving.fxml", actionEvent.getSource());
    }

    @FXML
    public void removeTaskPressed(ActionEvent actionEvent)
    {
        int idx = taskView.getSelectionModel().getSelectedIndex();
        if (idx != -1)
        {
            AppManager.getInstance().getDb().unassignTask(myTasks.get(idx).getId());
            reload();
        }
    }

    @FXML
    public void addTaskPressed(ActionEvent actionEvent) throws SQLException
    {
        int idx = unassignedTaskView.getSelectionModel().getSelectedIndex();
        if (idx != -1)
        {
            AppManager.getInstance().getDb().assignUnassignedTaskToDriver(myUnassignedTasks.get(idx).getId(), AppManager.getInstance().getDb().getUID(AppManager.getInstance().getUser()));
            reload();
        }
    }

    @FXML
    public void logoutPressed(ActionEvent actionEvent) throws IOException
    {
        DbBridge db = AppManager.getInstance().getDb();
        db.disconnect();
        AppManager.getInstance().switchView("Views/settings.fxml", actionEvent.getSource());
    }

    public void taskViewClicked(MouseEvent mouseEvent)
    {
        removeTaskButton.setDisable(taskView.getSelectionModel().getSelectedIndex() == -1);
    }

    public void unassignedTaskViewClicked(MouseEvent mouseEvent)
    {
        addTaskButton.setDisable(unassignedTaskView.getSelectionModel().getSelectedIndex() == -1);
    }
}