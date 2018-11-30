package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SetUndoActivityControllerTest {
    private SetUndoActivityController controller;

    private Account user = new Account("Hello", "World");

    @Before
    public void setUp(){
        StartingLoginActivity.currentUser = "Hello";
        SlidingTileComplexityActivity.complexity = 3;
        user.setCurrentGame(1);
        controller = new SetUndoActivityController(
                new FileSystem() {
                    public AccountManager loadAccount(Context context) {
                        List<Account> emptyAccounts = new ArrayList<>();
                        AccountManager accountManager = new AccountManager(emptyAccounts);
                        accountManager.addUser(user);
                        return accountManager;
                    }

                    public void saveAccount(Context context, AccountManager accountManager) {
                        user = accountManager.findUser("Hello");
                    }
                }
        );
    }

    @Test
    public void testSetUndoListenerValidNumber() {
        Context context = new AppCompatActivity();
        Boolean validUndo = controller.setUndoListener(context, "3");
        assertTrue(validUndo);

        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);
        SlidingTilesState state = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
    }

    @Test
    public void testSetUndoListenerInvalidNumber() {
        Context context = new AppCompatActivity();
        Boolean validUndo = controller.setUndoListener(context, "a");
        assertFalse(validUndo);
    }

    @Test
    public void testSetUndoListenerValidUnlimitedNumber() {
        Context context = new AppCompatActivity();
        Boolean validUndo = controller.setUndoListener(context, "unlimited");
        assertTrue(validUndo);
    }

    @Test
    public void testSetUndoListenerValidNoneNumber() {
        Context context = new AppCompatActivity();
        Boolean validUndo = controller.setUndoListener(context, "");
        assertTrue(validUndo);
        assertEquals(0, SetUndoActivityController.undo);
    }
}