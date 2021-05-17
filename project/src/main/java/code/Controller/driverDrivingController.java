package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class driverDrivingController implements Initializable
{
    @FXML
    Button arrivedButton;

    @FXML
    Button taskCompleteButton;

    @FXML
    Button continueDriveButton;

    @FXML
    Label taskAddressText;

    private int myActiveTaskId;

    public void arrivedPressed(ActionEvent actionEvent)
    {
        //Could message the server and say "We're at the task."
        arrivedButton.setVisible(false);
        taskCompleteButton.setVisible(true);
    }

    public void reload() throws SQLException
    {
        DbBridge dbBridge = AppManager.getInstance().getDb();
        ArrayList<Task> tasks = dbBridge.getDriverTasksUndone(dbBridge.getUID(AppManager.getInstance().getUser()));

        myActiveTaskId = -1;

        if (tasks.size() > 0)
        {
            myActiveTaskId = tasks.get(0).getId();
            taskAddressText.setText(tasks.get(0).getAddress());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        myActiveTaskId = -1;

        try
        {
            reload();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
    }

    public void taskCompletedPressed(ActionEvent actionEvent)
    {
        taskCompleteButton.setVisible(false);

        AppManager.getInstance().getDb().completeTask(myActiveTaskId);

        try
        {
            reload();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        if (myActiveTaskId != -1)
            continueDriveButton.setVisible(true);
        else
        {
            try
            {
                AppManager.getInstance().switchView("Views/driver.fxml", actionEvent.getSource());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void continueDrivePressed(ActionEvent actionEvent)
    {
        //Also, could message the server when starting a task and say "hey, we're going now to the charity!!!"
        continueDriveButton.setVisible(false);
        arrivedButton.setVisible(true);
    }
}