package code.Controller;

import code.Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class driverController implements Initializable
{
    @FXML
    ListView<DriverTask> taskView;

    @FXML
    ListView<Task> unassignedTaskView;

    @FXML
    Button beginDriveButton;

    @FXML
    Button addTaskButton;

    @FXML
    Button removeTaskButton;

    @FXML
    Button settings;

    @FXML
    Button logoutButton;

    DriverTaskList myTaskList;

    ArrayList<DriverTask> myAssignedTasks = new ArrayList<>();
    ArrayList<Task> myUnassignedTasks = new ArrayList<>();

    private void setupTasks()
    {
        DbBridge dbBridge = AppManager.getInstance().getDb();

        try
        {
            dbBridge.createDriverTaskList(dbBridge.getUID(AppManager.getInstance().getUser()));

            ArrayList<Task> tasks = dbBridge.getDriverTasksUndone(dbBridge.getUID(AppManager.getInstance().getUser()));
            myTaskList.refresh();

            ArrayList<DriverTask> driverTasks = myTaskList.getDriverTasks();

            for (DriverTask driverTask : driverTasks)
            {
                for (int i = tasks.size() - 1; i >= 0; --i)
                {
                    if (tasks.get(i).getId() == driverTask.getTaskID())
                    {
                        tasks.remove(i);
                        break;
                    }
                }
            }

            myTaskList.clearCharities();

            for (Task task : tasks)
            {
                myTaskList.addTask(task.getId());
            }

            myTaskList.fixCharities();

            myUnassignedTasks.addAll(dbBridge.getDriverTasksUndone(-1));
        } catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        myAssignedTasks.addAll(myTaskList.getDriverTasks());

        taskView.setItems(FXCollections.observableArrayList(myAssignedTasks));
        unassignedTaskView.setItems(FXCollections.observableArrayList(myUnassignedTasks));
    }

    public void reload()
    {
        myUnassignedTasks.clear();
        myAssignedTasks.clear();
        setupTasks();

        beginDriveButton.setDisable(false);

        if (myAssignedTasks.size() == 0)
        {
            beginDriveButton.setDisable(true);
        }

        taskViewClicked();
        unassignedTaskViewClicked();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            myTaskList = new DriverTaskList(AppManager.getInstance().getDb().getUID(AppManager.getInstance().getUser()));
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        taskView.setCellFactory(cell -> new ListCell<>()
        {
            final Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(DriverTask task, boolean empty)
            {
                super.updateItem(task, empty);

                if (task == null || empty)
                {
                    setText(null);
                    setTooltip(null);
                }
                else
                {
                    DbBridge dbBridge = AppManager.getInstance().getDb();

                    String text = null;
                    if (task.isTask())
                    {
                        text = "Pickup: ";
                    }
                    else
                    {
                        try
                        {
                            text = dbBridge.getUsersNameById(task.getCharityID()) + ": ";
                        }
                        catch (SQLException throwables)
                        {
                            throwables.printStackTrace();
                        }
                    }

                    text += task.getAddress();

                    setText(text);

                    String informationText = "";
                    if (task.isTask())
                    {
                        informationText += "Pickup\n";
                    }
                    else
                    {
                        informationText += "Charity delivery\n";
                    }

                    if (task.isCharity())
                    {
                        try
                        {
                            informationText += "Name: " + dbBridge.getUsersNameById(task.getCharityID()) + "\n";
                        }
                        catch (SQLException throwables)
                        {
                            throwables.printStackTrace();
                        }
                    }

                    informationText += "Address: " + task.getAddress();

                    if (task.isTask())
                    {
                        String charityName = null;
                        try
                        {
                            int charityId = dbBridge.getCharityIdFromTask(task.getTaskID());
                            charityName = dbBridge.getUsersNameById(charityId);
                        }
                        catch (SQLException throwables)
                        {
                            throwables.printStackTrace();
                        }
                        informationText += "\nCharity: " + charityName;
                    }

                    tooltip.setText(informationText);
                    tooltip.setShowDelay(new Duration(20));
                    setTooltip(tooltip);
                }
            }
        });

        unassignedTaskView.setCellFactory(cell -> new ListCell<>()
        {
            final Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Task task, boolean empty)
            {
                super.updateItem(task, empty);

                if (task == null || empty)
                {
                    setText(null);
                    setTooltip(null);
                } else
                {
                    setText("Pickup: " + task.getAddress());

                    String informationText = "Pickup\nAddress: " + task.getAddress();

                    tooltip.setText(informationText);
                    tooltip.setShowDelay(new Duration(20));
                    setTooltip(tooltip);
                }
            }
        });

        reload();
    }

    @FXML
    public void beginDrivePressed(ActionEvent actionEvent) throws IOException
    {
        AppManager.getInstance().switchView("Views/driverDriving.fxml", actionEvent.getSource());
    }

    @FXML
    public void removeTaskPressed()
    {
        int idx = taskView.getSelectionModel().getSelectedIndex();
        if (idx != -1)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove task?");
            alert.setContentText("Remove task?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent())
            {
                if (result.get() == ButtonType.OK)
                {
                    DriverTask task = myTaskList.getDriverTasks().get(idx);
                    if (task.getTaskID() >= -1)
                        AppManager.getInstance().getDb().unassignTask(task.getTaskID());

                    reload();
                }
            }
        }
    }

        @FXML
        public void addTaskPressed () throws SQLException
        {
            int idx = unassignedTaskView.getSelectionModel().getSelectedIndex();
            if (idx != -1)
            {
                AppManager.getInstance().getDb().assignUnassignedTaskToDriver(myUnassignedTasks.get(idx).getId(), AppManager.getInstance().getDb().getUID(AppManager.getInstance().getUser()));
                reload();
            }
        }

        @FXML
        public void logoutPressed (ActionEvent actionEvent) throws IOException
        {
            DbBridge db = AppManager.getInstance().getDb();
            db.disconnect();
            AppManager.getInstance().switchView("Views/login.fxml", actionEvent.getSource());
        }

        public void taskViewClicked ()
        {
            int selectedDriverTaskIndex = taskView.getSelectionModel().getSelectedIndex();
            boolean removeTaskDisabled = true;
            if (selectedDriverTaskIndex != -1)
            {
                if (myTaskList.getDriverTasks().get(selectedDriverTaskIndex).isTask())
                    removeTaskDisabled = false;
            }
            removeTaskButton.setDisable(removeTaskDisabled);
        }

        public void unassignedTaskViewClicked ()
        {
            addTaskButton.setDisable(unassignedTaskView.getSelectionModel().getSelectedIndex() == -1);
        }

        public void settingsPressed(ActionEvent event) throws IOException
        {
        AppManager.getInstance().switchView("Views/settings.fxml", event.getSource());
        }
    }