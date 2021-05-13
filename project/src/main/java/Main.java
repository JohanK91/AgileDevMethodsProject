import code.Model.AppManager;
import code.Model.DbBridge;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main
{


    public static void main(String[] args) throws SQLException {
        DbBridge db = AppManager.getInstance().getDb();
        ArrayList<Object> values = new ArrayList<Object>();
        values.add("address");
        values.add("city");
        values.add("postcode");
        values.add(1);
        values.add(2);
        db.createAddress(values);

        values = new ArrayList<Object>();
        values.add("user");
        values.add("name");
        values.add(1);
        values.add(0202);
        values.add("sadsadasd");
        values.add(1);
        db.createUser(values);
        db.disconnect();



    }
}