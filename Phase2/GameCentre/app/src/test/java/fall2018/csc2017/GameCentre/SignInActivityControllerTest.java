package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SignInActivityControllerTest {

    private SignInActivityController controller;

    private AccountManager accountManager = new AccountManager(new ArrayList<Account>());

    @Before
    public void setUp(){
        controller = new SignInActivityController(
                new FileSystem() {
                    public AccountManager loadAccount(Context context) {
                        return accountManager;
                    }

                    public void saveAccount(Context context, AccountManager accountManager) {
                    }

                    void testExistingFile(Context context) {
                    }
                }
        );
    }

    @Test
    public void testEnterButtonListenerTrue() {
        Context context = new AppCompatActivity();
        accountManager.addUser(new Account("Hello", "World"));
        Boolean successful = controller.enterButtonListener(context, "Hello", "World");
        assertTrue(successful);
    }

    @Test
    public void testEnterButtonListenerFalse() {
        Context context = new AppCompatActivity();
        Boolean successful = controller.enterButtonListener(context, "Hello", "World");
        assertFalse(successful);
    }
}