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
    Boolean okInput = true;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
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
        DbBridge db = AppManager.getInstance().getDb();
        String user = userName.getText();
        if (db.getUID(user) > 0 || user.isEmpty()) {
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
            ArrayList<Object> values = new ArrayList<>();
            if (db.getAddressID(street) == -1) {
                values.add(street);
                values.add(city);
                values.add(postcode);
                values.add(parseInt(x));
                values.add(parseInt(y));
                db.createAddress(values);
            }
            values = new ArrayList<>();
            values.add(user);
            values.add(name);
            values.add(type);
            values.add(phone);
            values.add(pass);
            values.add(db.getAddressID(street));
            db.createUser(values);
        }
    }
    @FXML
    private void handleLoginPressed(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource("Views/login.fxml"));
        Scene newScene= new Scene(p);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }
    public void invalid(String input){
        okInput = false;
        text.setText(text.getText()+"Invalid "+ input+ "\n");

    }
}
