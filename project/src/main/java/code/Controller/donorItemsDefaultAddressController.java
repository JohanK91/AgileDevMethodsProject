package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.UserType;
import com.mysql.cj.util.StringUtils;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class donorItemsDefaultAddressController implements Initializable
{

    @FXML
    TextField listItem;
    @FXML
    Button ButtonAddItem;
    @FXML
    Button ButtonAddCharity;
    @FXML
    Button ButtonAddDay;
    @FXML
    Button ButtonAddTime;
    @FXML
    TextField listDay;
    @FXML
    TextField listTime;
    @FXML
    TextField listCharity;
    @FXML
    Button back;
    @FXML
    Text textItem;
    @FXML
    Text textCharity;
    @FXML
    Text welcome;
    @FXML
    Text textDay;
    @FXML
    Text textTime;


    private void handleWelcomeText() {
        String name = AppManager.getInstance().getUser();
        welcome.setText("");
        welcome.setText(welcome.getText() + name + ", what items do you wish to donate?");
        textTime.setText("");
        textDay.setText("");
        textItem.setText("");
        textCharity.setText("");

    }

    @FXML
    private void handleBackPressed(ActionEvent event) throws IOException {
        switchView("Views/donorDecideAddress.fxml", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleWelcomeText();
    }

    private void switchView(String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void handleItemChoiceMenuPressed(ActionEvent event) throws IOException, SQLException {
        DbBridge db = AppManager.getInstance().getDb();

    }

    @FXML
    private void handleAddItemPressed(ActionEvent event) throws IOException {
        textItem.setText("");
        String item = listItem.getText();
        textItem.setText(textItem.getText() + item + " added");
        DbBridge db = AppManager.getInstance().getDb();

    }


    @FXML
    private void handleAddCharityPressed(ActionEvent event) throws IOException {
        textCharity.setText("");
        String charity = listCharity.getText();
        textCharity.setText(textCharity.getText() + charity + " added");
        DbBridge db = AppManager.getInstance().getDb();

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
