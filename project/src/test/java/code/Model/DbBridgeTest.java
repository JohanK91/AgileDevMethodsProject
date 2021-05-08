package code.Model;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

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
        assertFalse(myDBBridge.lookForAddress("address"));
        myDBBridge.createAddress("address",  "city",
                "postcode",22, 22);
        assertTrue(myDBBridge.lookForAddress("address"));

        assertFalse(myDBBridge.lookForUserName("test"));
        myDBBridge.createUser( "address", "test",
                "name",  0,  "phone",  "pass");
        assertTrue(myDBBridge.lookForUserName("test"));

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