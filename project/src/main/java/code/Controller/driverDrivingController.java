package code.Controller;

import code.Model.AppManager;
import code.Model.DriverTaskList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    Button taskIncompleteButton;

    @FXML
    Label taskAddressText;

    private DriverTaskList myTaskList;

    public void arrivedPressed()
    {
        //Could message the server and say "We're at the task."
        arrivedButton.setVisible(false);
        if (myTaskList.getActiveTask().isTask())
            taskIncompleteButton.setVisible(true);
        taskCompleteButton.setVisible(true);
    }

    public void reload() throws SQLException
    {
        myTaskList.refresh();

        if (myTaskList.hasTaskLeft())
        {
            String address = myTaskList.getActiveTask().getAddress();
            if (myTaskList.getActiveTask().isCharity())
                address += " (Charity)";
            taskAddressText.setText(address);
        }
        else
        {
            taskAddressText.setText(null);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            myTaskList = new DriverTaskList(AppManager.getInstance().getDb().getUID(AppManager.getInstance().getUser()));
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
        taskIncompleteButton.setVisible(false);

        myTaskList.completeTaskAndAdvance();

        try
        {
            reload();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        if (myTaskList.getActiveTask() != null)
            continueDriveButton.setVisible(true);
        else
        {
            try
            {
                myTaskList.close();
                AppManager.getInstance().switchView("Views/driver.fxml", actionEvent.getSource());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void taskIncompletePressed(ActionEvent actionEvent)
    {
        taskCompleteButton.setVisible(false);
        taskIncompleteButton.setVisible(false);

        myTaskList.advanceIterator();

        try
        {
            reload();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        if (myTaskList.getActiveTask() != null)
            continueDriveButton.setVisible(true);
        else
        {
            try
            {
                myTaskList.close();
                AppManager.getInstance().switchView("Views/driver.fxml", actionEvent.getSource());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void continueDrivePressed()
    {
        //Also, could message the server when starting a task and say "hey, we're going now to the charity!!!"
        continueDriveButton.setVisible(false);
        arrivedButton.setVisible(true);
    }
}