package Controller;

import Model.ActiveUser;
import Model.DbBridge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Button register;

    @FXML
    private void handleLoginPressed(ActionEvent event) throws IOException {
        String user = userName.getText();
        String pass = password.getText();
        DbBridge db = new DbBridge();
        if(db.validateLogIn(user,pass)){
            ActiveUser.getInstance(user,db.getUserType(user));
            switch (ActiveUser.getInstance().getType()) {
                // 0=donor, 1=driver, 2=charity
                case 0:
                    switchView(("../Views/donor.fxml"), (Stage)((Node)event.getSource()).getScene().getWindow());
                    break;
                case 1:
                    switchView(("../Views/driver.fxml"), (Stage)((Node)event.getSource()).getScene().getWindow());
                    break;
                case 2:
                   switchView(("../Views/charity.fxml"), (Stage)((Node)event.getSource()).getScene().getWindow());
                    break;
            }
        }
    }

    private void switchView(String view, Stage x) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= x;
        stage.setScene(newScene);
        stage.show();
    }
}
