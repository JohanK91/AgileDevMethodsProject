package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.Task;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class charityTaskController implements Initializable {
    DbBridge db;
    ArrayList<Task> myTasks;
    ArrayList<Integer> myIDs = new ArrayList<>();
    ArrayList<String> taskItems= new ArrayList<>();
    Integer activeTask;

    @FXML
    TextField donor;
    @FXML
    TextField driver;
    @FXML
    TextField eta;
    @FXML
    TextField start;
    @FXML
    TextField status;

    @FXML
    TextArea taskDescription;
    @FXML
    ListView<String> itemTypes;
    @FXML
    ListView<Integer> taskList;
    @FXML
    Text user;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user.setText(AppManager.getInstance().getUser());
        reload();
    }

    private void setupTask(){
        db= AppManager.getInstance().getDb();
        try {
            myTasks = db.getCharityTask(db.getUID(AppManager.getInstance().getUser()));
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        for (Task task : myTasks) {
            myIDs.add(task.getId());
        }
        taskList.setItems(FXCollections.observableArrayList(myIDs));

    }
    private void setText(int taskId) {
        db= AppManager.getInstance().getDb();
        ArrayList<String> info = db.getCharityTaskInfo(taskId);

        taskDescription.setText(info.get(0));
        donor.setText(info.get(1));
        driver.setText(info.get(2));
        eta.setText(info.get(3));
        start.setText(info.get(4));
        status.setText(info.get(5));
    }
    public void reload() {
        myIDs.clear();
        taskItems.clear();
        setupTask();
        if(!(activeTask == null))
            setText(activeTask);
    }
    public void taskViewClick(){
        activeTask = taskList.getSelectionModel().getSelectedItem();
        reload();
    }
    private void switchView (String view, ActionEvent event) throws IOException {
        db.disconnect();
        AppManager.getInstance().switchView(view, event.getSource());
    }
}
