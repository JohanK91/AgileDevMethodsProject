import Model.DbBridge;

public class Main {


    public static void main(String[] args) {
        DbBridge db = new DbBridge();
        System.out.println("doner1 pass " + db.validateLogIn("doner1","pass"));
        System.out.println("doner1 user1 "+ db.validateLogIn("doner1","user1"));
        System.out.println("doner2 pass " + db.validateLogIn("doner2","pass"));
        System.out.println("doner2 user2 " + db.validateLogIn("doner2","user2"));
        System.out.println("doner3 user2 " + db.validateLogIn("doner3","user2"));


        db.disconnect();
    }
}