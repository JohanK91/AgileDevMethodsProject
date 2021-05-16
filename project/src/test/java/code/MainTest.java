package code;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

class MainTest extends ApplicationTest
{
    @Override
    public void start (Stage stage) throws Exception
    {
        Parent mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("Views/login.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @AfterEach
    public void tearDown () throws Exception
    {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Disabled
    @Test
    public void testRegister ()
    {
        clickOn("#register");
        //clickOn("#userName");
        //write("driver");
        //clickOn("#password");
        //#write("driver");
        //clickOn("#login");
    }
}