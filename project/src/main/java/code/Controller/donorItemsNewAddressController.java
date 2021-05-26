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

public class donorItemsNewAddressController implements Initializable
{

    Boolean okInput = true;
    @FXML
    TextField streetField;
    @FXML
    TextField cityField;
    @FXML
    TextField postcodeField;
    @FXML
    Button register;
    @FXML
    Button nextButton;
    @FXML
    Text invalidText;
    @FXML
    Text addressCreatedText;
    @FXML
    Text clickNextText;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invalidText.setText("");
        addressCreatedText.setText("");
        clickNextText.setText("");
    }

    @FXML
    private void handleRegisterPressed(ActionEvent event) throws IOException, SQLException {

        invalidText.setText("");
        DbBridge db = AppManager.getInstance().getDb();

        String street = streetField.getText();
        if (street.isEmpty()){
            invalid("Street");
        }
        String city  = cityField.getText();
        if (city.isEmpty()){
            invalid("City");
        }
        String postcode = postcodeField.getText();
        if (postcode.isEmpty() || !StringUtils.isStrictlyNumeric(postcode)){
            invalid("Postcode");
        }

        if(okInput) {
            addressCreatedText.setText("Address created\nNew address used as pickup address." +
                      "\nStreet: " + street +
                      "\nCity: " + city +
                      "\nPostcode: " + postcode);
            clickNextText.setText("Click next!");
            ArrayList<Object> values = new ArrayList<>();
                int x = 11;
                int y = 22;
                values.add(street);
                values.add(city);
                values.add(postcode);
                values.add(x);
                values.add(y);
                db.createAddress(values);

        }
    }

    @FXML
    private void handleNextButtonPressed(ActionEvent event) throws IOException, SQLException {
        switchView("Views/donorItemsDefaultAddress.fxml", event);

    }

    private void switchView(String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }


    public void invalid(String input){
        okInput = false;
        invalidText.setText(invalidText.getText()+"Invalid "+ input+ "\n");
        addressCreatedText.setText("Something went wrong.\nPlease ensure valid inputs.");

    }
}
