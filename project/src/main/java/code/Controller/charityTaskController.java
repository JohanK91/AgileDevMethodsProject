package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.ItemType;
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
    TextArea itemDescription;

    @FXML
    ListView<String> itemTypes;
    @FXML
    ListView<Integer> taskList;
    @FXML
    ListView<Integer> taskListCompleted;

    @FXML
    Text user;

    @FXML
    Button completeButton;
    @FXML
    Button logout;
    @FXML
    Button settings;


    DbBridge db;
    ArrayList<Task> incomingTasks;
    ArrayList<Task> completedTasks;
    ArrayList<ItemType> taskItems;

    ArrayList<Integer> inCompletedIDs = new ArrayList<>();
    ArrayList<Integer> completedIDs = new ArrayList<>();
    ArrayList<String> listItems= new ArrayList<>();

    Integer activeTask;
    String activeItemType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user.setText(AppManager.getInstance().getUser());
        reload();
        completeButton.setDisable(true);
    }

    private void setupTask(){
        db= AppManager.getInstance().getDb();
        try {
            incomingTasks = db.getCharityTask(db.getUID(AppManager.getInstance().getUser()),"");
            completedTasks = db.getCharityTask(db.getUID(AppManager.getInstance().getUser()),
                    "Completed");

        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        for (Task task : incomingTasks) {
            inCompletedIDs.add(task.getId());
        }
        taskList.setItems(FXCollections.observableArrayList(inCompletedIDs));

        for (Task task : completedTasks) {
            completedIDs.add(task.getId());
        }
        taskListCompleted.setItems(FXCollections.observableArrayList(completedIDs));


    }
    private void setDetails() {
        db= AppManager.getInstance().getDb();
        ArrayList<String> info = db.getCharityTaskInfo(activeTask);

        taskDescription.setText(info.get(0));
        donor.setText("Donor: "+info.get(1));
        driver.setText("Driver: "+info.get(2));
        eta.setText("End: "+info.get(3));
        start.setText("Start: "+info.get(4));
        status.setText("Status: "+info.get(5));

        if(status.getText().equalsIgnoreCase("Status: arrivedToCharity")){
            completeButton.setDisable(false);
        }
        else  completeButton.setDisable(true);

        //items
        taskItems = db.getTaskItemTypes(activeTask);
        for (ItemType itemType : taskItems) {
            listItems.add(itemType.getName());
            }
        itemTypes.setItems(FXCollections.observableArrayList(listItems));
        if(activeItemType!=null){
            for (ItemType itemType : taskItems) {
                if(activeItemType.equalsIgnoreCase(itemType.getName()))
                    itemDescription.setText(itemType.getDescription());
            }
        }
    }

    public void reload() {
        itemDescription.setText(" ");
        inCompletedIDs.clear();
        completedIDs.clear();
        listItems.clear();
        setupTask();
        if(!(activeTask == null)) {
            setDetails();
        }
    }
    public void taskViewClick(){
        activeTask = taskList.getSelectionModel().getSelectedItem();
        activeItemType=null;
        reload();
    }
    public void taskListCompletedClick(){
        activeTask = taskListCompleted.getSelectionModel().getSelectedItem();
        activeItemType=null;
        reload();
    }

    public void itemListClick(){
        activeItemType =itemTypes.getSelectionModel().getSelectedItem();
        reload();
    }

    public void completeButtonPressed(ActionEvent event){
        db.execute("UPDATE task SET status = 'completed' WHERE ID ='"+activeTask+"'");
        reload();
    }
    public void logoutPressed(ActionEvent event) throws IOException {
        db.disconnect();
        AppManager.getInstance().switchView("Views/login.fxml", event.getSource());
    }
    public void settingsPressed(ActionEvent event) throws IOException {
        AppManager.getInstance().switchView("Views/settings.fxml", event.getSource());
    }

}
