package Model;
import java.sql.*;
public class DbBridge {

    private String mysqlUrl = "jdbc:mysql://localhost:3306/mydb2?";
    private Connection connection;
    private PreparedStatement  statement;
    private ResultSet resultSet;


    public DbBridge() {
        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(mysqlUrl, "root", "root");
            System.out.println("Connected");
        } catch (SQLException er) {
            System.out.println("Connection failed");
            er.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (!connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }

            if (statement != null) {
                statement.close();
                System.out.println("Statement closed");
            }
            if (resultSet != null) {
                resultSet.close();
                System.out.println("ResultSet closed");
            }

        }
        catch (SQLException er) {
            System.out.println("Failed to disconnect!");
            er.printStackTrace();

        }
    }

    public void createUser(String address,String userName, String name, int type, String phone, String pass){
        try {
            statement = connection.prepareStatement("INSERT INTO USER(userName, name, type, phone, pass," +
                    " Adress_adress) Values(?,?,?,?,?,?)");
            statement.setString(1, userName);
            statement.setString(2, name);
            statement.setInt(3, type);
            statement.setString(4, phone);
            statement.setString(5, pass);
            statement.setString(6, address);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createAddress(String address, String city, String postcode,int x, int y){
        try {
            statement = connection.prepareStatement("INSERT INTO Adress(adress, city, postcode, x, y)" +
                    "Values(?,?,?,?,?)");
            statement.setString(1, address);
            statement.setString(2, city);
            statement.setString(3, postcode);
            statement.setInt(4, x);
            statement.setInt(5, y);
            statement.executeUpdate();
        }
            catch (SQLException e) {
                e.printStackTrace();
        }

    }

    public boolean validateLogIn(String username, String password) {
        try {
            statement = connection.prepareStatement("SELECT userName FROM USER");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if(username.equals(resultSet.getString(1))){
                    statement = connection.prepareStatement("SELECT pass FROM USER WHERE userName = ?");
                    statement.setString(1, username);
                    resultSet = statement.executeQuery();
                    resultSet.next();
                    if (password.equals(resultSet.getString(1))){
                        return true;
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getUserType(String username) throws SQLException {

            statement = connection.prepareStatement("SELECT type FROM USER WHERE userName = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);

    }

    public boolean lookForUserName(String username){
        try {
            statement = connection.prepareStatement("SELECT userName FROM USER");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (username.equals(resultSet.getString(1))){
                    return true;
                }
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean lookForAddress(String street){
        try {
            statement = connection.prepareStatement("SELECT Adress FROM adress");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (street.equals(resultSet.getString(1))){
                    return true;
                }
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public ResultSet getAllTask() {
        return resultSet;
    }

    public ResultSet getDriversTask(String driverUserName) {
        return resultSet;
    }

    public ResultSet getCharityTask(String charityUserName) {
        return resultSet;
    }

    public Connection getConnection()  {
        return connection;
    }
    public ResultSet getResultSet()  {
        return resultSet;
    }
    public Statement getStatement()  {
        return statement;
    }
    public void removeUserAddress(String user, String address){
        try {
            statement = connection.prepareStatement(" DELETE FROM USER " +
                    "WHERE Username = ?");
            statement.setString(1, user);
            statement.executeUpdate();

            statement = connection.prepareStatement(" DELETE FROM Adress " +
                    "WHERE Adress = ?");
            statement.setString(1, address);
            statement.executeUpdate();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}