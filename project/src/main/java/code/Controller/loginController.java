package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.UserType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Button register;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setDefaultButton(true);
        DbBridge db = AppManager.getInstance().getDb();
        db.connect();

    }

        @FXML
        private void handleLoginPressed (ActionEvent event) throws IOException, SQLException, InterruptedException {
            String user = userName.getText();
            String pass = password.getText();
            DbBridge db = AppManager.getInstance().getDb();
            Thread.sleep(1500);


            if (db.validateLogIn(user, pass)) {
                AppManager.getInstance().setUser(user);
                switch (UserType.values()[db.getUserType(user)]) {
                    case Donor -> switchView("Views/donor.fxml", event);
                    case Driver -> switchView("Views/driver.fxml", event);
                    case Charity -> switchView("Views/charity.fxml", event);
                }
            }
        }

        @FXML
        private void handleRegisterPressed (ActionEvent event) throws IOException {
            switchView("Views/newAccount.fxml", event);

        }

        private void switchView (String view, ActionEvent event) throws IOException {
            Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
            Scene newScene = new Scene(p);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        }

    }


