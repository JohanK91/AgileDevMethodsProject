package code.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

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
        execute("INSERT INTO ADDRESS(street, city, postcode, x, y) Values(?,?,?,?,?)",values);

    }

    public boolean validateLogIn(String username, String password) {
        // will try to log in
        try {
            execute("SELECT userName FROM USER");
            while (resultSet.next()) {
                if(username.equalsIgnoreCase(resultSet.getString(1))){
                    execute("SELECT pass FROM USER WHERE lower(userName) ='"+username.toLowerCase()+"'");
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
        execute("SELECT type FROM USER WHERE lower(username) ='"+username.toLowerCase()+"'");
        resultSet.next();
        return resultSet.getInt(1);

    }
    public int getUID(String username) throws SQLException {
        //returns id for user if found else return -1
        execute("SELECT ID,USERNAME FROM USER");
        while (resultSet.next()) {
            if (resultSet.getString("USERNAME").equalsIgnoreCase(username))
                return resultSet.getInt("ID");
        }
        return -1;
    }
    public int getAddressID(String street) throws SQLException {
        //returns id for address if found else return -1
        execute("SELECT ID,street FROM ADDRESS");
            while (resultSet.next()) {
                if(resultSet.getString("street").equalsIgnoreCase(street))
                    return resultSet.getInt("ID");
            }
        return -1;
    }
    public ArrayList<String> getUserInfo(String user)  {
        ArrayList<String> output = new ArrayList<>();
        user = user.toLowerCase();
        try {
            switch (UserType.values()[getUserType(user)]) {
                case Donor -> {
                    execute("SELECT name,phone,street,city,postcode " +
                            "FROM user LEFT JOIN address ON address_id = address.id " +
                            "WHERE lower(userName) = '" + user + "'");
                }
                case Driver -> {
                    execute("SELECT name,phone,street,city,postcode,status " +
                            "FROM user LEFT JOIN address ON address_id = address.id " +
                            "LEFT JOIN driver ON user.id = User_ID " +
                            "WHERE lower(userName) = '" + user + "'");
                }
                case Charity -> {
                    execute("SELECT name,phone,street,city,postcode,webpage," +
                            "description FROM user LEFT JOIN address " +
                            "ON address_id = address.id LEFT JOIN charity " +
                            "ON user.id = User_ID WHERE lower(userName) = '" + user + "'");
                }
            }
            resultSet.next();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                output.add(resultSet.getObject(i).toString());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }




    public ArrayList<Task> getCharityTask(int chID,String value) {
        ArrayList<Task> tasks = new ArrayList<>();
        if(value.equalsIgnoreCase("completed"))
            execute("SELECT ID FROM task WHERE Charity_User_ID ='"+chID+"'AND " +
                    "Status = 'Completed'");

        else execute("SELECT ID FROM task WHERE Charity_User_ID ='"+chID+"'AND " +
                " NOT Status = 'Completed'");

        try {
            while (resultSet.next())
            {
                tasks.add(new Task(resultSet.getInt(1)));
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return tasks;
    }
    public ArrayList<String> getCharityTaskInfo(int taskId) {
        ArrayList<String> output = new ArrayList<>();
        execute("SELECT description,donor.userName,driver.userName," +
                "end_date,start_date, status FROM task LEFT JOIN user as donor " +
                "ON Donor_ID = donor.ID LEFT JOIN user as driver ON " +
                "Driver_User_ID = driver.ID WHERE task.ID =" + taskId);
        try {
            resultSet.next();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                if(resultSet.getObject(i)==null) output.add("Unassigned");
                else output.add(resultSet.getObject(i).toString());
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }
    public ArrayList<ItemType> getTaskItemTypes(int taskID) {
        ArrayList<ItemType> output = new ArrayList<>();

            execute("SELECT task.id,itemtype.name,itemtype.description " +
                    "FROM task LEFT JOIN task_has_itemtype ON task.ID = task_has_itemtype.Task_ID " +
                    "Left Join itemtype On task_has_itemtype.itemType_ID=itemtype.ID " +
                    "where task.id=" + taskID );

        try {
            while(resultSet.next()) {
                output.add(new ItemType(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3)));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
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
                        "WHERE lower(Username) = ?");
                statement.setString(1, user.toLowerCase());
                statement.executeUpdate();
            }
            while(getAddressID(street) > 0) {
                statement = connection.prepareStatement(" DELETE FROM Address " +
                        "WHERE lower(street) = ?");
                statement.setString(1, street.toLowerCase());
                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //Task
    public String getTaskAddress(int anId)
    {
        try
        {
            statement = connection.prepareStatement("SELECT address.street FROM address LEFT JOIN task ON task.pickupAddress_ID = address.ID WHERE task.ID = ?");
            statement.setInt(1, anId);
            resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getString(1);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return null;
    }

    public ArrayList<Task> getDriverTasksUndone(int aDriverId)
    {
        ArrayList<Task> tasks = new ArrayList<>();
        try
        {
            if (aDriverId == -1)
            {
                statement = connection.prepareStatement("SELECT ID FROM task WHERE Driver_User_ID IS NULL AND (NOT status = 'completed' OR status IS NULL)");
            }
            else
            {
                statement = connection.prepareStatement("SELECT ID FROM task WHERE Driver_User_ID = ? AND (NOT status = 'completed' OR status IS NULL)");
                statement.setInt(1, aDriverId);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                tasks.add(new Task(resultSet.getInt(1)));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tasks;
    }

    public void completeTask(int anId)
    {
        try
        {
            statement = connection.prepareStatement("UPDATE task SET status = 'completed' WHERE ID = ?");
            statement.setInt(1, anId);
            statement.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void assignUnassignedTaskToDriver(int aTaskId, int aDriverId)
    {
        try
        {
            statement = connection.prepareStatement("UPDATE task SET Driver_User_ID = ? WHERE ID = ?");
            statement.setInt(1, aDriverId);
            statement.setInt(2, aTaskId);
            statement.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void unassignTask(int aTaskId)
    {
        try
        {
            statement = connection.prepareStatement("UPDATE task SET Driver_User_ID = NULL WHERE ID = ?");
            statement.setInt(1, aTaskId);
            statement.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }


}