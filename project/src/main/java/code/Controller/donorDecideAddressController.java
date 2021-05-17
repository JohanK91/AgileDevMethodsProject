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

public class donorDecideAddressController implements Initializable
{

    @FXML
    Text welcome;
    @FXML
    Text defaultText;
    @FXML
    Text NewAddText;
    @FXML
    Button defaultAddress;
    @FXML
    Button logout;
    @FXML
    Button newAddress;


    private void handleWelcomeText() {
        String name = AppManager.getInstance().getUser();
        welcome.setText("");
        welcome.setText(welcome.getText() + name +  ",\n" +
                "Please click on one of the two buttons");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleWelcomeText();
    }


    @FXML
    private void handleDefaultAddressPressed(ActionEvent event) throws IOException {
        switchView("Views/donorItemsDefaultAddress.fxml", event);
    }
    @FXML
    private void handleNewAddressPressed(ActionEvent event) throws IOException {
        switchView("Views/donorItemsNewAddress.fxml", event);
    }

    @FXML
    private void handleBackPressed(ActionEvent event) throws IOException {
        switchView("Views/donor.fxml", event);
    }


    private void switchView(String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }


}