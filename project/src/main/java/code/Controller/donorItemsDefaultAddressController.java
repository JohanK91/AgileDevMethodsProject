package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.ItemType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import javafx.scene.Scene;
=======
>>>>>>> 9506777ae358a58037b059a3a150405356e8699e
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
<<<<<<< HEAD
import javafx.stage.Stage;

=======
>>>>>>> 9506777ae358a58037b059a3a150405356e8699e
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class donorItemsDefaultAddressController implements Initializable
{
<<<<<<< HEAD
    @FXML
    TextArea itemTypeDescriptionText;
    @FXML
    TextArea addedItemTypeDescriptionText;
    @FXML
    Button donateItemsButton;
    @FXML
=======
    @FXML
    TextArea itemTypeDescriptionText;
    @FXML
    TextArea addedItemTypeDescriptionText;
    @FXML
    Button donateItemsButton;
    @FXML
>>>>>>> 9506777ae358a58037b059a3a150405356e8699e
    Button ButtonAddItem;
    @FXML
    Button ButtonRemoveItem;
    @FXML
    Button setCharityButton;
    @FXML
    Label desiredCharityLabel;
    @FXML
    Button back;
    @FXML
    Text textCharity;
    @FXML
    Text welcome;
    @FXML
    DatePicker datePicker;
    @FXML
    SplitMenuButton timeMenuChoice;
    @FXML
    ListView<String> itemListView;
    @FXML
    ListView<String> addedItemListView;
    @FXML
    ListView<String> CharityListView;

    ArrayList<String> itemListViewArray = new ArrayList<>();
    ArrayList<Integer> itemTypesViewArrayIds = new ArrayList<>();

    ArrayList<String> addedItemListViewArray = new ArrayList<>();
    ArrayList<Integer> addedItemTypesViewArrayIds = new ArrayList<>();

    ArrayList<ItemType> itemTypes = new ArrayList<>();

    ArrayList<String> CharityListViewArray = new ArrayList<>();

    String selectedCharity = "None";

    private void handleWelcomeText() {
<<<<<<< HEAD
        welcome.setText(welcome.getText() + " " + AppManager.getInstance().getUser() + ", what items do you wish to donate?");
=======
        welcome.setText(welcome.getText() + AppManager.getInstance().getUser() + ", what items do you wish to donate?");
>>>>>>> 9506777ae358a58037b059a3a150405356e8699e
    }

    public void timeMenu() {
        MenuItem choice1 = new MenuItem("08-12");
        MenuItem choice2 = new MenuItem("12-16");
        MenuItem choice3 = new MenuItem("16-20");

        timeMenuChoice.getItems().addAll(choice1, choice2, choice3);
        timeMenuChoice.setText(timeMenuChoice.getItems().get(0).getText());

        choice1.setOnAction((e)-> timeMenuChoice.setText(choice1.getText()));
        choice2.setOnAction((e)-> timeMenuChoice.setText(choice2.getText()));
        choice3.setOnAction((e)-> timeMenuChoice.setText(choice3.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleWelcomeText();
        timeMenu();

        try
        {
            handleListViewStart();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

        itemListView.setItems(FXCollections.observableArrayList(itemListViewArray));
        addedItemListView.setItems(FXCollections.observableArrayList(addedItemListViewArray));

        CharityListView.setItems(FXCollections.observableArrayList(CharityListViewArray));

        ButtonAddItem.setDisable(true);
        ButtonRemoveItem.setDisable(true);
        setCharityButton.setDisable(true);
    }

    private void handleListViewStart() throws SQLException
    {
        DbBridge db = AppManager.getInstance().getDb();
        itemTypes = db.getItemTypes();
        itemListViewArray.clear();
        for (ItemType itemType : itemTypes)
        {
            itemListViewArray.add(itemType.getName());
            itemTypesViewArrayIds.add(itemType.getId());
        }
        CharityListViewArray = db.getCharityNameDesc();
        reloadItem();
        reloadCharity();
    }

    @FXML
    private void handleAddItemPressed() {
        int clickItem = itemListView.getSelectionModel().getSelectedIndex();
        if (clickItem != -1) {
            itemListView.getSelectionModel().clearSelection();

            addedItemTypesViewArrayIds.add(itemTypesViewArrayIds.get(clickItem));
            itemTypesViewArrayIds.remove(clickItem);

            addedItemListViewArray.add(itemListViewArray.get(clickItem));
            itemListViewArray.remove(clickItem);
        }

        reloadItem();
    }

    public void reloadItem() {
        itemListView.setItems(FXCollections.observableArrayList(itemListViewArray));
        addedItemListView.setItems(FXCollections.observableArrayList(addedItemListViewArray));

        ButtonAddItem.setDisable(itemListView.getSelectionModel().getSelectedIndex() == -1);
        ButtonRemoveItem.setDisable(addedItemListView.getSelectionModel().getSelectedIndex() == -1);

        if (ButtonAddItem.isDisabled())
            itemTypeDescriptionText.setText(null);
        if (ButtonRemoveItem.isDisabled())
            addedItemTypeDescriptionText.setText(null);
    }

    @FXML
    private void handleRemoveItemPressed() {
        int clickAddedItem = addedItemListView.getSelectionModel().getSelectedIndex();
        if (clickAddedItem != -1) {
            addedItemListView.getSelectionModel().clearSelection();

            itemTypesViewArrayIds.add(addedItemTypesViewArrayIds.get(clickAddedItem));
            addedItemTypesViewArrayIds.remove(clickAddedItem);

            itemListViewArray.add(addedItemListViewArray.get(clickAddedItem));
            addedItemListViewArray.remove(clickAddedItem);
        }

        reloadItem();
    }

    public void reloadCharity() {
        CharityListView.setItems(FXCollections.observableArrayList(CharityListViewArray));
    }

    @FXML
    private void handleBackPressed(ActionEvent event) throws IOException {
        AppManager.getInstance().switchView("Views/donorDecideAddress.fxml", event.getSource());
<<<<<<< HEAD
    }

    public void handleSetCharityPressed()
    {
        int selectedCharityIdx = CharityListView.getSelectionModel().getSelectedIndex();
        if (selectedCharityIdx != -1)
        {
            selectedCharity = CharityListViewArray.get(selectedCharityIdx);
            desiredCharityLabel.setText(selectedCharity);
        }
    }

    @FXML
    public void charityListViewClicked()
    {
        setCharityButton.setDisable(CharityListView.getSelectionModel().getSelectedIndex() == -1);
    }

    @FXML
    public void itemListViewClicked()
    {
        boolean itemSelected = itemListView.getSelectionModel().getSelectedIndex() != -1;
        ButtonAddItem.setDisable(!itemSelected);

        if (itemSelected)
        {
            int selectedIdx = itemListView.getSelectionModel().getSelectedIndex();
            for (ItemType itemType : itemTypes)
            {
                if (itemType.getId() == itemTypesViewArrayIds.get(selectedIdx))
                {
                    itemTypeDescriptionText.setText(itemType.getDescription());
                    break;
                }
            }
        }
        else
        {
            itemTypeDescriptionText.setText(null);
=======
    }

    public void handleSetCharityPressed()
    {
        int selectedCharityIdx = CharityListView.getSelectionModel().getSelectedIndex();
        if (selectedCharityIdx != -1)
        {
            selectedCharity = CharityListViewArray.get(selectedCharityIdx);
            desiredCharityLabel.setText(selectedCharity);
>>>>>>> 9506777ae358a58037b059a3a150405356e8699e
        }
    }

    @FXML
<<<<<<< HEAD
    public void addedItemListViewClicked()
    {
        boolean itemSelected = addedItemListView.getSelectionModel().getSelectedIndex() != -1;
        ButtonRemoveItem.setDisable(!itemSelected);

        if (itemSelected)
        {
            int selectedId = addedItemTypesViewArrayIds.get(addedItemListView.getSelectionModel().getSelectedIndex());
            for (ItemType itemType : itemTypes)
            {
                if (itemType.getId() == selectedId)
                {
                    addedItemTypeDescriptionText.setText(itemType.getDescription());
                    break;
                }
            }
        }
        else
        {
            addedItemTypeDescriptionText.setText(null);
        }
=======
    public void charityListViewClicked()
    {
        setCharityButton.setDisable(CharityListView.getSelectionModel().getSelectedIndex() == -1);
>>>>>>> 9506777ae358a58037b059a3a150405356e8699e
    }

    @FXML
    public void itemListViewClicked()
    {
        boolean itemSelected = itemListView.getSelectionModel().getSelectedIndex() != -1;
        ButtonAddItem.setDisable(!itemSelected);

        if (itemSelected)
        {
            int selectedIdx = itemListView.getSelectionModel().getSelectedIndex();
            for (ItemType itemType : itemTypes)
            {
                if (itemType.getId() == itemTypesViewArrayIds.get(selectedIdx))
                {
                    itemTypeDescriptionText.setText(itemType.getDescription());
                    break;
                }
            }
        }
        else
        {
            itemTypeDescriptionText.setText(null);
        }
    }

<<<<<<< HEAD
    public static void showStage(){
            Stage newStage = new Stage();
            VBox comp = new VBox();
            Text confirmText = new Text("Donation confirmed!");
            Text thanksText = new Text("Thank you for using D3!");
            Text goodbyeText = new Text("You may now close the application.");
            confirmText.setX(50);
            confirmText.setY(50);
            thanksText.setX(50);
            thanksText.setY(50);
            confirmText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            thanksText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            comp.getChildren().add(confirmText);
            comp.getChildren().add(thanksText);
            comp.getChildren().add(goodbyeText);

        //Setting the color
           // confirmText.setFill(Color.WHITE);

//Setting the Stroke
           // confirmText.setStrokeWidth(1);
//Setting the stroke color
           // confirmText.setStroke(Color.BLACK);
        //thanksText.setUnderline(true);

        Scene stageScene = new Scene(comp, 300, 70);
            newStage.setScene(stageScene);
            newStage.show();
        }
=======
    @FXML
    public void addedItemListViewClicked()
    {
        boolean itemSelected = addedItemListView.getSelectionModel().getSelectedIndex() != -1;
        ButtonRemoveItem.setDisable(!itemSelected);

        if (itemSelected)
        {
            int selectedId = addedItemTypesViewArrayIds.get(addedItemListView.getSelectionModel().getSelectedIndex());
            for (ItemType itemType : itemTypes)
            {
                if (itemType.getId() == selectedId)
                {
                    addedItemTypeDescriptionText.setText(itemType.getDescription());
                    break;
                }
            }
        }
        else
        {
            addedItemTypeDescriptionText.setText(null);
        }
    }
>>>>>>> 9506777ae358a58037b059a3a150405356e8699e

    @FXML
    public void donateItemsPressed(ActionEvent actionEvent) throws IOException, SQLException
    {
        DbBridge dbBridge = AppManager.getInstance().getDb();
        int charityId = dbBridge.getUIDFromName(selectedCharity);
        int userId = dbBridge.getUID(AppManager.getInstance().getUser());
        int addressId = dbBridge.getUserAddressId(userId);

        LocalDate day = datePicker.getValue();
<<<<<<< HEAD

        String time = timeMenuChoice.getText();

=======

        String time = timeMenuChoice.getText();

>>>>>>> 9506777ae358a58037b059a3a150405356e8699e
        String timeStart = time.substring(0, 2);
        String timeEnd = time.substring(3, 5);

        boolean okInput = charityId != -1;
        okInput &= addressId != -1;
        okInput &= addedItemTypesViewArrayIds.size() > 0;
        okInput &= day != null;

        if (okInput)
        {
            String startDate = day + ", " + timeStart;
            String endDate = day + ", " + timeEnd;
            DbBridge.TaskData taskData = new DbBridge.TaskData("Desc", startDate, endDate,
                    userId, addressId, charityId, addedItemTypesViewArrayIds);
            dbBridge.createTask(taskData);
            handleBackPressed(actionEvent);
<<<<<<< HEAD
            showStage();
=======
>>>>>>> 9506777ae358a58037b059a3a150405356e8699e
        }
    }
}