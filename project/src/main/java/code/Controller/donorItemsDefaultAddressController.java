package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import javafx.collections.FXCollections;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class donorItemsDefaultAddressController implements Initializable
{
    Boolean okInput = true;
    @FXML
    TextField listItem;
    @FXML
    Button ButtonAddItem;
    @FXML
    Button ButtonRemoveItem;
    @FXML
    Button ButtonAddCharity;
    @FXML
    Button dayConfirmButton;
    @FXML
    Button ButtonAddTime;
    @FXML
    TextField listDay;
    @FXML
    TextField listTime;
    @FXML
    TextField listCharity;
    @FXML
    Button back;
    @FXML
    Text textItem;
    @FXML
    Text textCharity;
    @FXML
    Text welcome;
    @FXML
    Text textDay;
    @FXML
    Text textTime;
    @FXML
    Text invalidText;
    @FXML
    DatePicker datePicker;
    @FXML
    SplitMenuButton timeMenuChoice;
    @FXML
    ListView<String> itemListView;
    @FXML
    ListView<String> addedItemListView;
    String itemtypeName;
    String itemtypeDescription;
    @FXML
    ListView<String> CharityListView;
    @FXML
    ListView<String> addedCharityListView;

    @FXML
    String timeChoice;

    ArrayList<String> itemListViewArray = new ArrayList<>();
    ArrayList<String> addedItemListViewArray = new ArrayList<>();

    ArrayList<String> CharityListViewArray = new ArrayList<>();
    ArrayList<String> addedCharityListViewArray = new ArrayList<>();



    private void handleWelcomeText() {
        String name = AppManager.getInstance().getUser();
        welcome.setText("");
        welcome.setText(welcome.getText() + name + ", what items do you wish to donate?");
        textTime.setText("");
        textDay.setText("");
        textItem.setText("");
        textCharity.setText("");


    }


    public void timeMenu() throws SQLException {
        MenuItem choice1 = new MenuItem("08-12");
        MenuItem choice2 = new MenuItem("12-16");
        MenuItem choice3 = new MenuItem("16-20");

        timeMenuChoice.getItems().addAll(choice1, choice2, choice3);
        DbBridge db = AppManager.getInstance().getDb();

        textDay.setText("");
        String userName = AppManager.getInstance().getUser();
        int donorId = db.getUID(userName);

        choice1.setOnAction((e)-> {
            textTime.setText("08-12 selected for pickup");
            timeChoice = "08-12";
            LocalDate day = datePicker.getValue();
            textDay.setText(textDay.getText() + day + " added");
            db.addDateToTask(day, timeChoice, donorId);

        });
        choice2.setOnAction((e)-> {
            textTime.setText("12-16 selected for pickup");
            timeChoice = "12-16";
            LocalDate day = datePicker.getValue();
            textDay.setText(textDay.getText() + day + " added");
            db.addDateToTask(day, timeChoice, donorId);

        });
        choice3.setOnAction((e)-> {
            textTime.setText("16-20 selected for pickup");
            timeChoice = "16-20";
            LocalDate day = datePicker.getValue();
            textDay.setText(textDay.getText() + day + " added");
            db.addDateToTask(day, timeChoice, donorId);

        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleWelcomeText();
        try {
            timeMenu();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try { handleListViewStart(); } catch (SQLException throwables) {
            throwables.printStackTrace(); }

        itemListView.setItems(FXCollections.observableArrayList(itemListViewArray));
        addedItemListView.setItems(FXCollections.observableArrayList(addedItemListViewArray));

        CharityListView.setItems(FXCollections.observableArrayList(CharityListViewArray));
        addedCharityListView.setItems(FXCollections.observableArrayList(addedCharityListViewArray));

    }




    private void handleListViewStart() throws SQLException {
        DbBridge db = AppManager.getInstance().getDb();
        ArrayList<String> test = new ArrayList<String>();
        itemListViewArray = db.getItemtypeNameDesc();
        CharityListViewArray = db.getCharityNameDesc();
        reloadItem();
        reloadCharity();

        //  for (String i : itemListViewArray) { System.out.println(i);System.out.println("Hello"); }
    }




    public ArrayList<String> getItemListViewArray() {
        return itemListViewArray;
    }

    public void setItemListViewArray(ArrayList<String> itemListViewArray) {
        this.itemListViewArray = itemListViewArray;
    }

    public ArrayList<String> getCharityListViewArray() {
        return CharityListViewArray;
    }

    public void setCharityListViewArray(ArrayList<String> charityListViewArray) {
        CharityListViewArray = charityListViewArray;
    }

    @FXML
    private void handleAddItemPressed(ActionEvent event) throws IOException {

        DbBridge db = AppManager.getInstance().getDb();
            String clickItem = itemListView.getSelectionModel().getSelectedItem();
            if (clickItem != null) {
                itemListView.getSelectionModel().clearSelection();
                addedItemListViewArray.add(clickItem);
                itemListViewArray.remove(clickItem);
            }
        textItem.setText("");
        textItem.setText(textItem.getText() + clickItem + " added");
            reloadItem();
        }



    public void reloadItem() {
        itemListView.setItems(FXCollections.observableArrayList(itemListViewArray));
        addedItemListView.setItems(FXCollections.observableArrayList(addedItemListViewArray));
    }

    @FXML
    private void handleRemoveItemPressed(ActionEvent event) throws IOException {
        DbBridge db = AppManager.getInstance().getDb();
        String clickAddedItem = addedItemListView.getSelectionModel().getSelectedItem();
        if (clickAddedItem != null) {
            addedItemListView.getSelectionModel().clearSelection();
            itemListViewArray.add(clickAddedItem);
            addedItemListViewArray.remove(clickAddedItem);
        }
        textItem.setText("");
        textItem.setText(textItem.getText() + clickAddedItem + " removed");

        reloadItem();

    }

    @FXML
    private void handleAddCharityPressed(ActionEvent event) throws IOException {
        DbBridge db = AppManager.getInstance().getDb();
        String clickCharity = CharityListView.getSelectionModel().getSelectedItem();
        if (clickCharity != null) {
            CharityListView.getSelectionModel().clearSelection();
            addedCharityListViewArray.add(clickCharity);
            CharityListViewArray.remove(clickCharity);
        }
        textCharity.setText("");
        textCharity.setText(textCharity.getText() + clickCharity + " added");
        reloadCharity();
    }

    @FXML
    private void handleRemoveCharityPressed(ActionEvent event) throws IOException {
        DbBridge db = AppManager.getInstance().getDb();
        String clickAddedCharity = addedCharityListView.getSelectionModel().getSelectedItem();
        if (clickAddedCharity != null) {
            addedCharityListView.getSelectionModel().clearSelection();
            CharityListViewArray.add(clickAddedCharity);
            addedCharityListViewArray.remove(clickAddedCharity);
        }
        textCharity.setText("");
        textCharity.setText(textCharity.getText() + clickAddedCharity + " removed");
        reloadCharity();

    }

    public void reloadCharity() {
        CharityListView.setItems(FXCollections.observableArrayList(CharityListViewArray));
        addedCharityListView.setItems(FXCollections.observableArrayList(addedCharityListViewArray));
    }



    @FXML
    private void handleAddDayPressed1(ActionEvent event) throws IOException, SQLException {

        textDay.setText("");
        LocalDate day = datePicker.getValue();
        textDay.setText(textDay.getText() + day + " added");
        DbBridge db = AppManager.getInstance().getDb();
        String userName = AppManager.getInstance().getUser();
        int donorId = db.getUID(userName);
       // db.addDateToTask(day, donorId);

    }

    @FXML
    private void handleBackPressed(ActionEvent event) throws IOException {
        switchView("Views/donorDecideAddress.fxml", event);
    }

    private void switchView(String view, ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        Scene newScene= new Scene(p);
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }


}
