package code.Model;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class DbBridgeTest
{

    private static DbBridge myDBBridge;

    @BeforeEach
    public void setUp() throws Exception
    {
        try
        {
            myDBBridge = new DbBridge();
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    @AfterEach
    public void afterEach() throws SQLException {
        TestDisconnection();
    }
    @AfterAll
    public static void afterAll(){
        myDBBridge.connect();
        myDBBridge.removeUserAddress("test","address");
        myDBBridge.disconnect();

    }
    @Test
    @DisplayName("Test disconnection.")
    public void TestDisconnection() throws SQLException {
        myDBBridge.disconnect();
        assertTrue(myDBBridge.getConnection().isClosed());
        assertTrue( myDBBridge.getStatement()== null ||
                myDBBridge.getStatement().isClosed());
        assertTrue(myDBBridge.getResultSet()== null  ||
                myDBBridge.getResultSet().isClosed());
    }
    @Test
    @DisplayName("Test createUser")
    public void TestReadWriteUser() throws SQLException {
        assertFalse(myDBBridge.getAddressID("address") > 0);
        ArrayList<Object> values = new ArrayList<Object>();
        values.add("address");
        values.add("city");
        values.add("postcode");
        values.add(1);
        values.add(2);
        myDBBridge.createAddress(values);
        assertTrue(myDBBridge.getAddressID("address") > 0);

        assertFalse(myDBBridge.getUID("test") > 0);

        values = new ArrayList<Object>();
        values.add("test");
        values.add("name");
        values.add(0);
        values.add("phone");
        values.add("pass");
        values.add(myDBBridge.getAddressID("address"));
        myDBBridge.createUser(values);

        assertTrue(myDBBridge.getUID("test") > 0);

        assertEquals(myDBBridge.getUserType("test"),0);
    }
    @Test
    @DisplayName("Test validateLogIn")
    public void TestValidateLogIn()
    {
        assertFalse(myDBBridge.validateLogIn("Test","pass"));
        assertFalse(myDBBridge.validateLogIn("test","Pass"));
        assertTrue(myDBBridge.validateLogIn("test","pass"));
    }
}