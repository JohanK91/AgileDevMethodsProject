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
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class donorController implements Initializable
{

    @FXML
    Text welcome;
    @FXML
    Button donate;
    @FXML
    Button logout;
    @FXML
    Button settings;


    private void handleWelcomeText() {
        String name = AppManager.getInstance().getUser();
        welcome.setText("");
        welcome.setText(welcome.getText() + name +  ", welcome to Uber Charity!\n" +
                "Thank you for generosity!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleWelcomeText();
    }



    @FXML
    private void handleDonatePressed(ActionEvent event) throws IOException {
        switchView("Views/donorDecideAddress.fxml", event);
    }
    @FXML
    private void handleSettingsPressed(ActionEvent event) throws IOException {
        switchView("Views/donorSettings.fxml", event);
    }
    @FXML
    private void handleLogoutPressed(ActionEvent event) throws IOException {
        DbBridge db = AppManager.getInstance().getDb();
        db.disconnect();
        switchView("Views/login.fxml", event);

    }


    private void switchView(String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }


}