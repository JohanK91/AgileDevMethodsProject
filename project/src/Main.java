import Controller.DbBridge;

import java.sql.*;

public class Main {


    public static void main(String[] args) {
        DbBridge db = new DbBridge();
        db.disconnect();
    }
}