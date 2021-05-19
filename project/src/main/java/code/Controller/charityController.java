package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class charityController implements Initializable {
    DbBridge db;

    @FXML
    TextField webpage;
    @FXML
    TextField phone;
    @FXML
    TextField username;
    @FXML
    Text name;
    @FXML
    TextField address;

    @FXML
    PasswordField oldpassword;
    @FXML
    PasswordField newpassword;

    @FXML
    TextArea CharityDescription;
    @FXML
    TextArea itemtype;

    @FXML
    Button taskView;
    @FXML
    Button logout;
    @FXML
    Button newaddress;
    @FXML
    Button newitemtype;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db=AppManager.getInstance().getDb();
        String user = AppManager.getInstance().getUser();
        ArrayList<String> info = db.getUserInfo(user);
        name.setText(info.get(0));
        phone.setText(info.get(1));
        address.setText(info.get(2));
        //city.setText(info.get(3));
        //postcode.setText(info.get(4));
        webpage.setText(info.get(5));
        CharityDescription.setText(info.get(6));


    }

    @FXML
    public void logoutPressed(ActionEvent actionEvent) throws IOException
    {
        switchView("Views/login.fxml", actionEvent);
    }
    @FXML
    public void newAddressPressed(ActionEvent actionEvent) throws IOException
    {
        switchView("Views/login.fxml", actionEvent);;
    }
    public void newItemPressed(ActionEvent actionEvent) throws IOException
    {
        switchView("Views/login.fxml", actionEvent);
    }
    public void taskViewPressed(ActionEvent actionEvent) throws IOException
    {
        switchView("Views/charityTask.fxml", actionEvent);
    }
    public void savePressed(ActionEvent actionEvent) throws IOException
    {
        switchView("Views/charityTask.fxml", actionEvent);
    }

    private void switchView (String view, ActionEvent event) throws IOException {
        db.disconnect();
        AppManager.getInstance().switchView(view, event.getSource());
    }
}
