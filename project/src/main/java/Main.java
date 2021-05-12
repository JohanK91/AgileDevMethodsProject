import code.Model.AppManager;
import code.Model.DbBridge;
import java.util.ArrayList;

public class Main
{


    public static void main(String[] args)
    {
        DbBridge db = AppManager.getInstance().getDb();
        ArrayList<Object> values = new ArrayList<Object>();
        values.add("address");
        values.add("city");
        values.add("postcode");
        values.add(1);
        values.add(2);
        db.createAddress(values);
        db.disconnect();



    }
}