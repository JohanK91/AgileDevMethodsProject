package Controller;
import java.sql.*;
public class DbBridge {

    private String mysqlUrl = "jdbc:mysql://localhost:3306/mydb2?";
    private Connection connection;

    public DbBridge()
    {
        connect("username", "P@ssword");
    }

    private void connect(String name,String pass) {
        try {
            connection = DriverManager.getConnection(mysqlUrl,name,pass);
            System.out.println("Connected");
        }
        catch (SQLException er) {
            System.out.println("Connection failed");
            er.printStackTrace();
        }
    }


    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");

            }
        } catch (SQLException er) {
            System.out.println("Failed to disconnect!");
            er.printStackTrace();

        }
    }
}