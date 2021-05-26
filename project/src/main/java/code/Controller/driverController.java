package code.Controller;

import code.Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

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

    DriverTaskList myTaskList;

    ArrayList<String> myUnassignedAddresses = new ArrayList<>();
    ArrayList<String> myAssignedAddresses = new ArrayList<>();

    ArrayList<Task> myUnassignedTasks;

    private void setupTasks()
    {
        DbBridge dbBridge = AppManager.getInstance().getDb();

        try
        {
            dbBridge.createDriverTaskList(dbBridge.getUID(AppManager.getInstance().getUser()));

            ArrayList<Task> tasks = dbBridge.getDriverTasksUndone(dbBridge.getUID(AppManager.getInstance().getUser()));
            myTaskList.refresh();

            ArrayList<DriverTask> driverTasks = myTaskList.getDriverTasks();

            for(DriverTask driverTask : driverTasks)
            {
                for (int i = tasks.size()-1; i >= 0; --i)
                {
                    if (tasks.get(i).getId() == driverTask.getTaskID())
                    {
                        tasks.remove(i);
                        break;
                    }
                }
            }

            myTaskList.clearCharities();

            for (Task task : tasks)
            {
                myTaskList.addTask(task.getId());
            }

            myTaskList.fixCharities();

            myUnassignedTasks = dbBridge.getDriverTasksUndone(-1);
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        for (DriverTask task : myTaskList.getDriverTasks())
        {
            String address = task.getAddress();
            if (task.isCharity())
                address += " (Charity)";
            myAssignedAddresses.add(address);
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

        taskViewClicked();
        unassignedTaskViewClicked();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            myTaskList = new DriverTaskList(AppManager.getInstance().getDb().getUID(AppManager.getInstance().getUser()));
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        reload();
    }

    @FXML
    public void beginDrivePressed(ActionEvent actionEvent) throws IOException
    {
        AppManager.getInstance().switchView("Views/driverDriving.fxml", actionEvent.getSource());
    }

    @FXML
    public void removeTaskPressed()
    {
        int idx = taskView.getSelectionModel().getSelectedIndex();
        if (idx != -1)
        {
            DriverTask task = myTaskList.getDriverTasks().get(idx);
            if (task.getTaskID() >= -1)
                AppManager.getInstance().getDb().unassignTask(task.getTaskID());

            reload();
        }
    }

    @FXML
    public void addTaskPressed() throws SQLException
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
        AppManager.getInstance().switchView("Views/login.fxml", actionEvent.getSource());
    }

    public void taskViewClicked()
    {
        int selectedDriverTaskIndex = taskView.getSelectionModel().getSelectedIndex();
        boolean removeTaskDisabled = true;
        if (selectedDriverTaskIndex != -1)
        {
            if (myTaskList.getDriverTasks().get(selectedDriverTaskIndex).isTask())
                removeTaskDisabled = false;
        }
        removeTaskButton.setDisable(removeTaskDisabled);
    }

    public void unassignedTaskViewClicked()
    {
        addTaskButton.setDisable(unassignedTaskView.getSelectionModel().getSelectedIndex() == -1);
    }
}