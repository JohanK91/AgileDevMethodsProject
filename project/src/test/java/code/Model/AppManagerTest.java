package code.Model;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class AppManagerTest
{
    AppManager myAppManager;

    @BeforeEach
    public void getInstanceTest()
    {
        myAppManager = AppManager.getInstance();
        assertNotNull(myAppManager);
    }

    @AfterEach
    public void afterEach()
    {
        myAppManager = AppManager.Destroy();
        assertNull(myAppManager);
    }

    @Test
    @DisplayName("getSetUserTest")
    public void getSetUserTest(){
        String user = "test";
        String user2 = "test2";

        myAppManager.setUser(user);
        assertNotEquals(user2,myAppManager.getUser());
        assertEquals(user,myAppManager.getUser());

        myAppManager.setUser(user2);
        assertNotEquals(user,myAppManager.getUser());
        assertEquals(user2,myAppManager.getUser());
    }
    @Test
    @DisplayName("getDbTest")
    public void getDb()
    {
        DbBridge myDb = AppManager.getInstance().getDb();
        assertNotNull(myDb);
    }

}
