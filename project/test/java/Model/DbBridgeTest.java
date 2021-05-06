package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DbBridgeTest
{
    private DbBridge myDBBridge;

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

    @Test
    @DisplayName("Test disconnection.")
    public void TestDisconnection()
    {
        myDBBridge.disconnect();
    }
}