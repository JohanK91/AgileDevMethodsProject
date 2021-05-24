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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class settingsController implements Initializable {
    DbBridge db;
    Boolean okInput;
    int type;
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
    TextField webbPage;
    @FXML
    TextField description;
    @FXML
    TextField itemDesc;
    @FXML
    TextField itemName;

    @FXML
    Button register;
    @FXML
    Button login;
    @FXML
    Button addNewItem;

    @FXML
    Text userCreatedText;
    @FXML
    Text textPage;
    @FXML
    Text textDesc;
    @FXML
    Text textAccept;

    @FXML
    ListView itemList;

    @FXML
    GridPane gridPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            db = AppManager.getInstance().getDb();
            db.connect();
            type = db.getUserType(AppManager.getInstance().getUser());
            switch (UserType.values()[type]) {
                case Donor, Driver -> {
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void hide() {
        gridPane.getColumnCount();
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 8);
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 9);

    }

    public void addNewItemPressed() {
        hide();

    }


}




