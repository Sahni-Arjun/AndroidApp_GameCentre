package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class HangmanStartingActivityControllerTest {

    /**
     * The slidingTileActivityController to test.
     */
    private HangmanStartingActivityController hangmanStartingActivityController;

    private WordManager wordManager;

    private Account user;

    /**
     * Make 3 moves.
     */
    private void setUpAccountWithGame() {
        user = new Account("Hello", "World");
        user.setCurrentGame(1);
        SaveManager saveManager = user.getCurrentSaveManager(Account.hangmanName);

        SlidingTileComplexityActivity.complexity = 3;
        HangmanComplexityActivity.complexity = 3;
        Word.numCols = HangmanComplexityActivity.complexity + 1;
        Word.numRows = 1;
        wordManager = new WordManager("Test");
        HangmanState hangmanState1 = new HangmanState(wordManager, 0, 3, 3, 0, false);
        saveManager.addState(hangmanState1);
        Word currWord = wordManager.getWord();
        // guess correct
        currWord.updateLetter(84);
    }

    @Before
    public void setUp(){
        StartingLoginActivity.currentUser = "Hello";
        setUpAccountWithGame();
        hangmanStartingActivityController = new HangmanStartingActivityController(
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

                    public Scoreboard loadScoreboard(Context context, String filename) {
                        return new Scoreboard();
                    }

                    void saveScoreBoard(Context context, String filename, Scoreboard scrboard) {
                    }
                }, new DisplayToast());
    }

    @Test
    public void testContinue() {
        HangmanStartingActivity hangmanStartingActivity = new HangmanStartingActivity();
        assertTrue(hangmanStartingActivityController.continueSave(1, hangmanStartingActivity));
    }

    @Test
    public void testLoadSave() {
        HangmanStartingActivity hangmanStartingActivity = new HangmanStartingActivity();
        assertFalse(hangmanStartingActivityController.loadSave(1, hangmanStartingActivity));

        // add perm save
        assertTrue(hangmanStartingActivityController.continueSave(1, hangmanStartingActivity));
        user.getCurrentSaveManager(Account.hangmanName).updateSave(SaveManager.perma);
        assertTrue(hangmanStartingActivityController.loadSave(1, hangmanStartingActivity));
    }
}
