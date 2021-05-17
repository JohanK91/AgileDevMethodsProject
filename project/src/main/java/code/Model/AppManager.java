package code.Model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//singleton
public class AppManager {
    private static AppManager instance = null;
    private String user;
    private DbBridge db;

    private AppManager(){
        this.db = new DbBridge();
    }

    public String getUser(){
        return this.user;
    }
    public DbBridge getDb(){
        return this.db;
    }
    public void setUser(String name){
        this.user = name;
    }

    private void DestroyInstance()
    {
        db.disconnect();
        db = null;
    }

    public static AppManager Destroy()
    {
        if (instance != null)
            instance.DestroyInstance();

        instance = null;
        return instance;
    }

    public static AppManager getInstance()
    {
        if (instance == null)
            instance = new AppManager();
        return instance;
    }

    public void switchView(String view, Object source) throws IOException
    {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene = new Scene(p);
        Stage stage = (Stage) ((Node) source).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }
}
