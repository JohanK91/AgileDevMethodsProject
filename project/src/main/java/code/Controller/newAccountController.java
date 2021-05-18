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

public class newAccountController implements Initializable
{
    int type;
    DbBridge db;
    Boolean okInput;

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
    Text userCreatedText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db= AppManager.getInstance().getDb();
        db.connect();
        text.setText("");
        userCreatedText.setText("");
        register.setDefaultButton(true);

        for (UserType userType : UserType.values())
        {
            MenuItem userTypeItem = new MenuItem(userType.toString());
            typeMenu.getItems().add(userTypeItem);
            userTypeItem.setOnAction((e)->{
                type = userType.ordinal();
                typeMenu.setText(userType.toString());
            });
        }

        typeMenu.setText(UserType.values()[0].toString());
        type = 0;
    }

    @FXML
    private void handleRegisterPressed(ActionEvent event) throws IOException, SQLException {
        // This is so dirty... please help
        text.setText("");
        userCreatedText.setText("");
        okInput=true;
        String user = userName.getText();
        if (db.getUID(user) > 0 || user.isEmpty()) {
            invalid("Username already exist or is empty");
        }
        String pass = password.getText();
        if (pass.isEmpty()){
            invalid("Password is empty");
        }
        String name = nameField.getText();
        if (name.isEmpty()){
            invalid("Name is empty");

        }
        String phone = phoneField.getText();
        if (phone.isEmpty() || !StringUtils.isStrictlyNumeric(phone)){
            invalid("Phone can only have numbers ");
        }

        String street= streetField.getText();
        if (street.isEmpty()){
            invalid("Street is empty");
        }
        String city  = cityField.getText();
        if (city.isEmpty()){
            invalid("City is empty");
        }
        String postcode = postcodeField.getText();
        if (postcode.isEmpty() || !StringUtils.isStrictlyNumeric(postcode)){
            invalid("Postcode can only have numbers");
        }
        String x = xField.getText();
        if (x.isEmpty() || !StringUtils.isStrictlyNumeric(x)){
            invalid("X can only have numbers");
        }
        String y = yField.getText() ;
        if (y.isEmpty() || !StringUtils.isStrictlyNumeric(y)){
            invalid("Y can only have numbers");

        }
        if(okInput) {

            ArrayList<Object> values = new ArrayList<>();
            if (db.getAddressID(capitalize(street)) == -1) {
                values.add(capitalize(street));
                values.add(capitalize(city));
                values.add(postcode);
                values.add(parseInt(x));
                values.add(parseInt(y));
                db.createAddress(values);
            }
            values = new ArrayList<>();
            values.add(user);
            values.add(capitalize(name));
            values.add(type);
            values.add(phone);
            values.add(pass);
            values.add(db.getAddressID(capitalize(street)));
            db.createUser(values);
            userCreatedText.setText("User creation success");
        }

    }
    @FXML
    private void handleLoginPressed(ActionEvent event) throws IOException {

        switchView("Views/login.fxml", event);
    }
    public void invalid(String input){
        okInput = false;
        text.setText(text.getText()+input+ "\n");

    }

    public static String capitalize(String input){
        if(input.isEmpty())
            return input;
        return  input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }
    private void switchView (String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene = new Scene(p);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }
}
