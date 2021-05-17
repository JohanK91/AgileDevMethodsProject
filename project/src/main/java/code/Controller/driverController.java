package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.Task;
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

    ArrayList<String> myUnassignedAddresses = new ArrayList<>();
    ArrayList<String> myAssignedAddresses = new ArrayList<>();

    ArrayList<Task> myTasks;
    ArrayList<Task> myUnassignedTasks;

    private void setupTasks()
    {
        DbBridge dbBridge = AppManager.getInstance().getDb();
        try
        {
            myTasks = dbBridge.getDriversTasks(dbBridge.getUID(AppManager.getInstance().getUser()));
            myUnassignedTasks = dbBridge.getDriversTasks(-1);
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
}
