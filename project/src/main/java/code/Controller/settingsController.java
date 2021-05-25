package code.Controller;

import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.ItemType;
import code.Model.UserType;
import com.mysql.cj.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class settingsController implements Initializable {

    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    PasswordField newPassword;
    @FXML
    TextField nameField;
    @FXML
    TextField phoneField;
    @FXML
    TextField streetField;
    @FXML
    TextField cityField;
    @FXML
    TextField postcodeField;
    @FXML
    TextField xField;
    @FXML
    TextField yField;
    @FXML
    TextField webbPage;
    @FXML
    TextField description;
    @FXML
    TextArea itemDesc;
    @FXML
    TextField itemName;

    @FXML
    Button remove;
    @FXML
    Button back;
    @FXML
    Button addNewItem;
    @FXML
    Button save;

    @FXML
    Text userCreatedText;
    @FXML
    Text textPage;
    @FXML
    Text textDesc;
    @FXML
    Text textAccept;

    @FXML
    ListView<String> itemList;

    @FXML
    RowConstraints grid0;
    @FXML
    RowConstraints grid1;
    @FXML
    RowConstraints grid2;

    DbBridge db;
    Boolean okInput;

    ArrayList<ItemType> charityItems;
    ArrayList<ItemType> allItems;

    ArrayList<String> listItems = new ArrayList<>();

    String activeItemType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            db = AppManager.getInstance().getDb();

            switch (UserType.values()[db.getUserType(AppManager.getInstance().getUser())]) {
                case Donor, Driver -> {
                    hide();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getData();
        reload();

    }

    private void reload() {
        listItems.clear();
        refreshItems();


    }

    private void refreshItems() {
        try {
            charityItems = db.getCharityItemTypes(db.getUID(AppManager.getInstance().getUser()));
            for (ItemType itemType : charityItems) {
                if (itemType.getName() != null)
                    listItems.add(itemType.getName());
            }
            itemList.setItems(FXCollections.observableArrayList(listItems));

            if (activeItemType != null) {
                for (ItemType itemType : charityItems) {
                    if (activeItemType.equalsIgnoreCase(itemType.getName())) {
                        itemName.setText(itemType.getName());
                        itemDesc.setText(itemType.getDescription());
                    }
                }
            }
            allItems = db.getAllItemType();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void itemListClick() {
        if (listItems.size() > 0)
            activeItemType = itemList.getSelectionModel().getSelectedItem();
        reload();
    }

    private void getData() {
        ArrayList<String> info = db.getUserInfo(AppManager.getInstance().getUser());
        userName.setText(AppManager.getInstance().getUser());

        nameField.setText(info.get(0));
        phoneField.setText(info.get(1));
        streetField.setText(info.get(2));
        cityField.setText(info.get(3));
        postcodeField.setText(info.get(4));
        xField.setText(info.get(5));
        yField.setText(info.get(6));

        if (grid0.getMaxHeight() > 0) {
            webbPage.setText(info.get(7));
            description.setText(info.get(8));
        }
    }

    private void setData() throws SQLException {
        okInput = true;
        String user = userName.getText();
        if (db.getUID(user) > 0 && !user.equalsIgnoreCase(AppManager.getInstance().getUser())
                || user.isEmpty()) {
            invalid("Username already exist or is empty");
        }
        String pass = password.getText();
        if (pass.isEmpty()) {
            invalid("Password is empty");
        }
        String name = nameField.getText();
        if (name.isEmpty()) {
            invalid("Name is empty");

        }
        String phone = phoneField.getText();
        if (phone.isEmpty() || !StringUtils.isStrictlyNumeric(phone)) {
            invalid("Phone can only have numbers ");
        }

        String street = streetField.getText();
        if (street.isEmpty()) {
            invalid("Street is empty");
        }
        String city = cityField.getText();
        if (city.isEmpty()) {
            invalid("City is empty");
        }
        String postcode = postcodeField.getText();
        if (postcode.isEmpty() || !StringUtils.isStrictlyNumeric(postcode)) {
            invalid("Postcode can only have numbers");
        }
        String x = xField.getText();
        if (x.isEmpty() || !StringUtils.isStrictlyNumeric(x)) {
            invalid("X can only have numbers");
        }
        String y = yField.getText();
        if (y.isEmpty() || !StringUtils.isStrictlyNumeric(y)) {
            invalid("Y can only have numbers");

        }
        if (grid0.getMaxHeight() > 0) {
            String webb = webbPage.getText();
            if (webb.isEmpty()) {
                invalid("webbPage is empty");
            }
            String desc = description.getText();
            if (desc.isEmpty()) {
                invalid("Charity Description is empty");
            }
        }
        if (okInput && db.validateLogIn(AppManager.getInstance().getUser(), pass)) {
            ArrayList<Object> values = new ArrayList<>();
            if (db.getAddressID(capitalize(street)) == -1) {
                values.add(capitalize(street));
                values.add(capitalize(city));
                values.add(postcode);
                values.add(parseInt(x));
                values.add(parseInt(y));
                db.createAddress(values);
            }
            values = new ArrayList<>();
            values.add(user);
            values.add(capitalize(name));
            values.add(phone);
            if (!newPassword.getText().isEmpty())
                pass = newPassword.getText();
            values.add(pass);
            values.add(db.getAddressID(capitalize(street)));
            if (grid0.getMaxHeight() > 0) {
                values.add(webbPage.getText());
                values.add(description.getText());
            }

            db.updateUser(AppManager.getInstance().getUser(), values);
            AppManager.getInstance().setUser(user);
            userCreatedText.setText("User updated");
            getData();
        } else
            userCreatedText.setText(userCreatedText.getText() + " Something went wrong, check your input");
    }

    public void hide() {
        grid0.setMaxHeight(0);
        grid1.setMaxHeight(0);
        grid2.setMaxHeight(0);

        textPage.setVisible(false);
        textDesc.setVisible(false);
        textAccept.setVisible(false);

        itemList.setVisible(false);
        webbPage.setVisible(false);
        description.setVisible(false);
        itemDesc.setVisible(false);
        itemName.setVisible(false);

        remove.setVisible(false);
        addNewItem.setVisible(false);

    }

    public void addNewItemPressed() throws SQLException {
        okInput = true;
        Boolean itemExist = false;
        String name = itemName.getText();
        String desc = itemDesc.getText();
        if (name.isEmpty()) {
            invalid("Item name is empty");
        }
        if (desc.isEmpty()) {
            invalid("Item description is empty");
        }
        if (listItems.size() > 0) {
            for (ItemType item : charityItems) {
                if (item.getName().equalsIgnoreCase(name) &&
                        item.getDescription().equalsIgnoreCase(desc)) {
                    itemExist = true;
                }
            }
        }
        if (!itemExist) {
            if (db.getItemTypeID(name, desc) == -1) {
                db.addItemType(name, desc);
            }
            db.addItemTypeToCharity(db.getUID(AppManager.getInstance().getUser()),
                    db.getItemTypeID(name, desc));
        }
        reload();
    }

    public void invalid(String input) {
        okInput = false;
        userCreatedText.setText(userCreatedText.getText() + input + "\n");

    }

    public void removePressed() throws SQLException {
        if (activeItemType != null) {
            for (ItemType item : charityItems) {
                if (item.getName() != null && item.getName().equalsIgnoreCase(activeItemType)) {
                    db.removeItemCharity(item.getId(), db.getUID(AppManager.getInstance().getUser()));
                }
            }
        }
        reload();
    }

    public void backPressed(ActionEvent event) throws SQLException, IOException {
        switch (UserType.values()[db.getUserType(AppManager.getInstance().getUser())]) {
            case Donor -> switchView("Views/donor.fxml", event);
            case Driver -> switchView("Views/driver.fxml", event);
            case Charity -> switchView("Views/charityTask.fxml", event);
        }
    }

    public void savePressed() throws SQLException {
        userCreatedText.setText("");
        setData();
    }

    public static String capitalize(String input) {
        if (input.isEmpty())
            return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    private void switchView(String view, ActionEvent event) throws IOException {
        AppManager.getInstance().switchView(view, event.getSource());
    }
}




