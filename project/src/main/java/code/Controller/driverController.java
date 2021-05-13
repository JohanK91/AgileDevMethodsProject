package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.Task;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
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

    ArrayList<String> myUnassignedAddresses;
    ArrayList<String> myAssignedAddresses;

    ArrayList<Task> myTasks;
    ArrayList<Task> myUnAssignedTasks;

    private void setupTasks()
    {
        DbBridge dbBridge = AppManager.getInstance().getDb();
        try
        {
            myTasks = dbBridge.getDriversTasks(dbBridge.getUID(AppManager.getInstance().getUser()));
            myUnAssignedTasks = dbBridge.getDriversTasks(-1);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        for (Task task : myTasks)
        {
            myAssignedAddresses.add(task.getAddress());
        }
        for (Task task : myUnAssignedTasks)
        {
            myUnassignedAddresses.add(task.getAddress());
        }

        taskView.setItems(FXCollections.observableArrayList(myAssignedAddresses));
        unassignedTaskView.setItems(FXCollections.observableArrayList(myUnassignedAddresses));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        myAssignedAddresses = new ArrayList<>();
        myUnassignedAddresses = new ArrayList<>();
        setupTasks();
    }
}
