package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    Button endDriveButton;

    @FXML
    Label taskAddressText;

    public void arrivedPressed(ActionEvent actionEvent)
    {
        arrivedButton.setVisible(false);
        taskCompleteButton.setVisible(true);
    }

    public void reload() throws SQLException
    {
        DbBridge dbBridge = AppManager.getInstance().getDb();
        ArrayList<Task> tasks = dbBridge.getDriversTasks(dbBridge.getUID(AppManager.getInstance().getUser()));

        if (tasks.size() > 0)
            taskAddressText.setText(tasks.get(0).getAddress());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            reload();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
    }
}