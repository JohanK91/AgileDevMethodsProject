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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class donorSettingsController implements Initializable {

    @FXML
    Label wish;
    @FXML
    Button oldPass;
    @FXML
    PasswordField changePasslist;
    @FXML
    Button changePass;
    @FXML
    TextField listAddress;
    @FXML
    Button ButtonAddCharity;
    @FXML
    PasswordField oldPasslist;
    @FXML
    Button back;
    @FXML
    Text textItem;
    @FXML
    Text oldPassText;
    @FXML
    Text passText;
    @FXML
    Text welcome;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String mysqlUrl = "jdbc:mysql://localhost:3306/mydb2?";
    private boolean correct;



    private void switchView(String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }


    private void handleWelcomeText() {
        String name = AppManager.getInstance().getUser();
        welcome.setText("");
        welcome.setText(welcome.getText() + name + ", alter your settings here!");
        oldPassText.setText("");
        passText.setText("");
        setCorrect(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleWelcomeText();
    }


    @FXML
    private void handleBackPressed(ActionEvent event) throws IOException {
        switchView("Views/donor.fxml", event);
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @FXML
    private void handleConfirmOldPassPressed(ActionEvent event) throws IOException, InterruptedException {
        oldPassText.setText("");
        passText.setText("");


        String user = AppManager.getInstance().getUser();
        String pass = oldPasslist.getText();
        DbBridge db = AppManager.getInstance().getDb();

        if (db.validateLogIn(user, pass)) {
            AppManager.getInstance().setUser(user);
            oldPassText.setText(oldPassText.getText() + "Password confirmed!");
            //boolean correct = true;
            setCorrect(true);

        } else {
            oldPassText.setText(oldPassText.getText() + "Wrong password!");
        }
    }

    @FXML
    private void handleChangePassPressed(ActionEvent event) throws IOException, InterruptedException, SQLException {
        passText.setText("");
        oldPassText.setText("");
        if (isCorrect()) {
            passText.setText(passText.getText() + "Password changed!");
            changePass();
        } else {
            passText.setText(passText.getText() + "You must first confirm your old password!");
        }
    }

    private void changePass() throws SQLException {
        String user = AppManager.getInstance().getUser();
        String pass = oldPasslist.getText();
        String newpass = changePasslist.getText();
        DbBridge db = AppManager.getInstance().getDb();
        connection = db.getConnection();

        if (db.validateLogIn(user, pass)) {
            AppManager.getInstance().setUser(user);

            statement = connection.prepareStatement("SELECT userName FROM USER");
            resultSet = statement.executeQuery();
            resultSet.next();
            statement = connection.prepareStatement("SELECT pass FROM user WHERE userName = " + "'" + user + "'");
            resultSet = statement.executeQuery();
            statement = connection.prepareStatement("UPDATE user SET pass = " + "'" + newpass + "'" + " WHERE userName = " + "'" + user + "'");
            statement.executeUpdate();

        }
    }
       // changePass.setOnAction(new EventHandler<ActionEvent>() {
         //   @Override public void handle(ActionEvent event) {
           //     passText.setText("");
            //}
        // });

}
