import code.Model.AppManager;
import code.Model.DbBridge;
import code.Model.ItemType;
import javafx.collections.FXCollections;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main
{


    public static void main(String[] args) throws SQLException {
        DbBridge db= AppManager.getInstance().getDb();

        ArrayList<ItemType> items = db.getCharityItemTypes(3);
        for (ItemType itemType:items) {
            System.out.println(itemType.getName());
        }

        }


    }
