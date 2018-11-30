package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SignUpActivityControllerTest {

    private SignUpActivityController controller;

    private AccountManager accountManager = new AccountManager(new ArrayList<Account>());

    @Before
    public void setUp(){
        controller = new SignUpActivityController(
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
    public void testEnterButtonListenerExistingAccount() {
        Context context = new AppCompatActivity();
        accountManager.addUser(new Account("Hello", "World"));
        String message = controller.enterButtonListener(context, "Hello", "World");
        String expected = "Username exists. Please choose a different one";

        assertEquals(expected, message);
    }

    @Test
    public void testEnterButtonListenerSuccessfulSignUp() {
        Context context = new AppCompatActivity();
        String message = controller.enterButtonListener(context, "Hello", "World");
        String expected = "Loading...";
        assertEquals(expected, message);
    }

    @Test
    public void testEnterButtonListenerTooShort() {
        Context context = new AppCompatActivity();
        String message = controller.enterButtonListener(context, "123", "World");
        String expected = "Username should be at least 4 characters long.";
        assertEquals(expected, message);
    }

    @Test
    public void testEnterButtonListenerTrailingSpaces() {
        Context context = new AppCompatActivity();
        String message = controller.enterButtonListener(context, "123 ", "World");
        String expected = "No trailing spaces in Username.";
        assertEquals(expected, message);
    }

    @Test
    public void testEnterButtonListenerNoWhiteSpaces() {
        Context context = new AppCompatActivity();
        String message = controller.enterButtonListener(context, "12 3", "World");
        String expected = "No whitespaces in Username.";
        assertEquals(expected, message);
    }

    @Test
    public void testEnterButtonListenerBadPassword() {
        Context context = new AppCompatActivity();
        String message = controller.enterButtonListener(context, "1234", "Worl d");
        String expected = "Please choose a different one";
        assertEquals(expected, message);
    }
}