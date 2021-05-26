package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class donorItemsController implements Initializable {


    @FXML
    Button addItem;
    @FXML
    TextField listItem;
    @FXML
    Button ButtonAddAddress;
    @FXML
    TextField listAddress;
    @FXML
    Button ButtonAddCharity;
    @FXML
    TextField listCharity;
    @FXML
    Button back;
    @FXML
    Text textItem;
    @FXML
    Text textAddress;
    @FXML
    Text textCharity;
    @FXML
    Text welcome;
    @FXML
    TextField listDay;
    @FXML
    TextField listTime;
    @FXML
    Text textDay;
    @FXML
    Text textTime;


    private void switchView(String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    private void handleWelcomeText() {
        String name = AppManager.getInstance().getUser();
        welcome.setText("");
        welcome.setText(welcome.getText() + name + ", what items do you wish to donate?");
        textTime.setText("");
        textDay.setText("");
        textItem.setText("");
        textAddress.setText("");
        textCharity.setText("");


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleWelcomeText();
    }

    @FXML
    private void handleBackPressed(ActionEvent event) throws IOException {
        switchView("Views/donor.fxml", event);
    }

    @FXML
    private void handleAddItemPressed(ActionEvent event) throws IOException {
        textItem.setText("");
        String item = listItem.getText();
        textItem.setText(textItem.getText() + item + " added");
        DbBridge db = AppManager.getInstance().getDb();

    }

    @FXML
    private void handleAddAddressPressed(ActionEvent event) throws IOException {
        textAddress.setText("");
        String address = listAddress.getText();
        textAddress.setText(textAddress.getText() + address + " added");

    }

    @FXML
    private void handleAddCharityPressed(ActionEvent event) throws IOException {
        textCharity.setText("");
        String charity = listCharity.getText();
        textCharity.setText(textCharity.getText() + charity + " added");

    }

    @FXML
    private void handleAddDayPressed(ActionEvent event) throws IOException {
        textDay.setText("");
        String day = listDay.getText();
        textDay.setText(textDay.getText() + day + " added");
        DbBridge db = AppManager.getInstance().getDb();

    }

    @FXML
    private void handleAddTimePressed(ActionEvent event) throws IOException {
        textTime.setText("");
        String time = listTime.getText();
        textTime.setText(textTime.getText() + time + " added");
        DbBridge db = AppManager.getInstance().getDb();

    }


}
