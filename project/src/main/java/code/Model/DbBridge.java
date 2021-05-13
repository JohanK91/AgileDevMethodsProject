package code.Model;
import java.sql.*;
import java.util.ArrayList;

public class DbBridge {

    final String mysqlUrl = "jdbc:mysql://localhost:3306/mydb2?";
    private Connection connection;
    private PreparedStatement  statement;
    private ResultSet resultSet;


    public DbBridge() {
        connect();
    }
    public void connect() {
        // will connect to database with url = mysqlUrl
        try {
            connection = DriverManager.getConnection(mysqlUrl, "root", "root");
            System.out.println("Connected");
        } catch (SQLException er) {
            System.out.println("Connection failed");
            er.printStackTrace();
        }
    }
    public void disconnect() {
        // will disconnect from the database

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

    public void createUser(ArrayList<Object> values) throws SQLException {
        // creates user based on input
        execute("INSERT INTO USER(userName, name, type, phone, pass, Address_ID) Values(?,?,?,?,?,?)"
                ,values);
        if((Integer) values.get(2)==1){
            execute("INSERT INTO Driver(USER_ID)Values("+getUID((String) values.get(0))+")");
        }
        if((Integer) values.get(2)==2){
            execute("INSERT INTO Charity(USER_ID)Values("+getUID((String) values.get(0))+")");

        }

    }
    public void createAddress(ArrayList<Object> values) {
        // create address based on input
        execute("INSERT INTO ADDRESS(address, city, postcode, x, y) Values(?,?,?,?,?)",values);

    }

    public boolean validateLogIn(String username, String password) {
        // will try to log in
        try {
            execute("SELECT userName FROM USER");
            while (resultSet.next()) {
                if(username.equals(resultSet.getString(1))){
                    execute("SELECT pass FROM USER WHERE userName ='"+username+"'");
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
        execute("SELECT type FROM USER WHERE username ='"+username+"'");
        resultSet.next();
        return resultSet.getInt(1);

    }
    public int getUID(String username) throws SQLException {
        //returns id for user if found else return -1
        execute("SELECT ID,USERNAME FROM USER");
        while (resultSet.next()) {
            if (resultSet.getString("USERNAME").equals(username))
                return resultSet.getInt("ID");
        }
        return -1;
    }
    public int getAddressID(String street) throws SQLException {
        //returns id for address if found else return -1
        execute("SELECT ID,ADDRESS FROM address");
            while (resultSet.next()) {
                if(resultSet.getString("ADDRESS").equals(street))
                    return resultSet.getInt("ID");
            }
        return -1;
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
    public void execute(String command){
        try {
            statement = connection.prepareStatement(command);
            if (command.contains("SELECT")) {
                resultSet = statement.executeQuery();
            }
            else {
                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String command, ArrayList<Object> values){
        try {
            statement = connection.prepareStatement(command);
            for(int i = 0; i < values.size(); i++){
                if(values.get(i) instanceof String){
                    statement.setString(i+1, (String) values.get(i));
                }
                else{
                    statement.setInt(i+1, (Integer) values.get(i));
                }
            }
            if(command.contains("SELECT")){
                resultSet = statement.executeQuery();
            }
            else {
                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserAddress(String user, String street){
        // removes all instances of user with user as username and all addresses with street as address
        try {
            while(getUID(user) > 0) {
                statement = connection.prepareStatement(" DELETE FROM USER " +
                        "WHERE Username = ?");
                statement.setString(1, user);
                statement.executeUpdate();
            }
            while(getAddressID(street) > 0) {
                statement = connection.prepareStatement(" DELETE FROM Address " +
                        "WHERE Address = ?");
                statement.setString(1, street);
                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}