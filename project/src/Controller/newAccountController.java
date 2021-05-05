package Controller;

import Model.AppManager;
import Model.DbBridge;
import com.mysql.cj.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;

import static java.lang.Integer.parseInt;

public class newAccountController {
    Boolean okInput = true;
    int type = 4;
    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    TextField nameField;
    @FXML
    TextField phoneField;
    @FXML
    TextField streetField;
    @FXML
    TextField cityField;
    @FXML
    TextField postcodeField;
    @FXML
    TextField xField;
    @FXML
    TextField yField;
    @FXML
    SplitMenuButton typeMenu;
    @FXML
    Button register;
    @FXML
    Button login;
    @FXML
    MenuItem donor;
    @FXML
    MenuItem driver;
    @FXML
    MenuItem charity;
    @FXML
    Text text;


    @FXML
    private void handleRegisterPressed(ActionEvent event) throws IOException {
        // This is so dirty... please help
        text.setText("");
        DbBridge db = AppManager.getInstance().getDb();
        String user = userName.getText();
        if (db.lookForUserName(user) || user.isEmpty()) {
            invalid("Username");
        }
        String pass = password.getText();
        if (pass.isEmpty()){
            invalid("Password");
        }
        String name = nameField.getText();
        if (name.isEmpty()){
            invalid("Name");

        }
        String phone = phoneField.getText();
        if (phone.isEmpty() || !StringUtils.isStrictlyNumeric(phone)){
            invalid("Phone");
        }
        if (type == 4){
            invalid("Type");
        }
        String street= streetField.getText();
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
        String x = xField.getText();
        if (x.isEmpty() || !StringUtils.isStrictlyNumeric(x)){
            invalid("X");
        }
        String y = yField.getText() ;
        if (y.isEmpty() || !StringUtils.isStrictlyNumeric(y)){
            invalid("Y");

        }
        if(okInput) {
            if (!db.lookForAddress(street)) {
                db.createAddress(street, city, postcode, parseInt(x), parseInt(y));
            }
            db.createUser(street, user, name, type, phone, pass);
        }
    }
    @FXML
    private void handleLoginPressed(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("../Views/login.fxml"));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }
    public void invalid(String input){
        okInput = false;
        text.setText(text.getText()+"Invalid "+ input+ "\n");

    }
    public void donorType(ActionEvent actionEvent) {
        typeMenu.setText("Donor");
        type = 0;
    }
    public void driverType(ActionEvent actionEvent) {
        typeMenu.setText("Driver");
        type = 1;
    }
    public void charityType(ActionEvent actionEvent) {
        typeMenu.setText("Charity");
        type = 2;
    }
}
