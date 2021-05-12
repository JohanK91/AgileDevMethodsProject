package code.Controller;

import code.Model.AppManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class driverController implements Initializable
{
    @FXML
    ListView<String> taskView;

    ArrayList<String> myAddresses;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        myAddresses = new ArrayList<>();

        ResultSet rs = AppManager.getInstance().getDb().getDriversTask(AppManager.getInstance().getUser());
        while (true)
        {
            try
            {
                if (!rs.next()) break;

                myAddresses.add(rs.getString(1));
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }

        taskView.setItems(FXCollections.observableArrayList(myAddresses));
    }
}
