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

public class settingsController
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



}
