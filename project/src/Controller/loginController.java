package Controller;

import Model.AppManager;
import Model.DbBridge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        DbBridge db = AppManager.getInstance().getDb();
        if(db.validateLogIn(user,pass)){
            AppManager.getInstance().setUser(user);
            switch (db.getUserType(user)) {
                case 0:
                    switchView("../Views/donor.fxml", event);
                    break;
                case 1:
                    switchView("../Views/driver.fxml", event);
                    break;
                case 2:
                   switchView("../Views/charity.fxml", event);
                    break;
            }
        }
    }
    @FXML
    private void handleRegisterPressed(ActionEvent event) throws IOException {
        switchView("../Views/newAccount.fxml", event);

    }

    private void switchView(String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }
}
