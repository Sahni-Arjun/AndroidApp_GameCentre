//package fall2018.csc2017.GameCentre;
//
//import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class WinningActivityControllerTest {
//
//    private WinningActivityController controller;
//
//    private Account user = new Account("Username", "Password");
//
//    @Before
//    public void setUp(){
//        final Scoreboard scoreboard = new Scoreboard();
//
//        scoreboard.addToScoreBoard(scoreboard.createScore("Username", 207));
//        StartingLoginActivity.currentUser = "Username";
//
//        controller = new WinningActivityController(
//                new FileSystem() {
//                    public AccountManager loadAccount(Context context) {
//                        List<Account> emptyAccounts = new ArrayList<>();
//                        AccountManager accountManager = new AccountManager(emptyAccounts);
//                        accountManager.addUser(user);
//                        return accountManager;
//                    }
//
//                    public Scoreboard loadScoreboard(Context context, String filename) {
//                        return scoreboard;
//                    }
//                }
//        );
//    }
//
//    @Test
//    public void testOnOpenListener() {
//        Context context = new AppCompatActivity();
//        user.setLastPlayedGame(Account.slidingName);
//        String displayedScore = controller.onOpenListener(context);
//        String expectedScore = "Your Score: 207";
//
//        assertEquals(expectedScore, displayedScore);
//    }
//
//    @Test
//    public void testFindFilename(){
//        Context context = new AppCompatActivity();
//        user.setLastPlayedGame(Account.slidingName);
//        String filename = controller.findFilename(context);
//        assertEquals(StartingLoginActivity.SAVE_SLIDING_SCOREBOARD, filename);
//
//        user.setLastPlayedGame(Account.hangmanName);
//        filename = controller.findFilename(context);
//        assertEquals(StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD, filename);
//
//        user.setLastPlayedGame(Account.sudokuName);
//        filename = controller.findFilename(context);
//        assertEquals(StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD, filename);
//
//        user.setLastPlayedGame("random");
//        filename = controller.findFilename(context);
//        assertEquals(StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD, filename);
//
//    }
//}