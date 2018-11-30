package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HangmanActivityControllerTest {

    private HangmanStartingActivity hangmanStartingActivity;

    private HangmanStartingActivityController hangmanStartingActivityController;

    private HangmanActivity hangmanActivity;

    private HangmanActivityController hangmanActivityController;

    private WordManager wordManager;

    private Account user;

    private AccountManager accountManager;

    private SaveManager saveManager;

    private int gameFile = 1;

    public FileSystem fileSystem;

    public DisplayToast displayToast;

    public Context context = hangmanActivity;


    /**
     * Make a starting Word.
     */
    private void setUpStartingWord() {


    }


    /**
     * Set up letters.
     */
    private List<Letter> setUpLetters(String word) {

        List<Letter> letters = new ArrayList<>();

        char[] splittedWord = word.toCharArray();

        for(int count=0; count<splittedWord.length; count++){

            char curChar = Character.toUpperCase(splittedWord[count]);
            int letterNum = curChar;
            letters.add(new Letter(letterNum));

        }

        return letters;
    }


    /**
     * Make 6 guesses for mocking
     */
    private void sixGuesses() {

        for(int i = 0; i < 6; i++) wordManager.keyBoard(65);

    }

    /**
     * Set account for mocking
     */
    private void setUpAccountWithGame() {


    }

    @Before
    public void setUp(){
//        fileSystem = new FileSystem();
//        displayToast = new DisplayToast();
//
//        fileSystem = new FileSystem();


//        hangmanActivity = new HangmanActivity();
//        context = hangmanStartingActivity;
//        hangmanActivityController = new HangmanActivityController(fileSystem, displayToast);
//        hangmanStartingActivity = new HangmanStartingActivity();
//        hangmanStartingActivityController = new HangmanStartingActivityController(fileSystem, displayToast);

        HangmanComplexityActivity.complexity = 4;
        SlidingTileComplexityActivity.complexity = 4;


        List<Letter> letters = setUpLetters("seven");

        Word word = new Word();
        Word.numRows = 1;
        Word.numCols = 5;
        word.setWord(5);
        word.setLetters(letters);
        wordManager = new WordManager(word);
        HangmanActivity.wordManager = wordManager;

        StartingLoginActivity.currentUser = "Hello";                                                                                              
        user = new Account("Hello", "Password");
        user.setCurrentGame(1);                                                                                                                   
        saveManager = user.getCurrentSaveManager(Account.hangmanName);                                                                            
        HangmanState hangmanState1 = new HangmanState(wordManager, 0, 4, 3, 0, true);
        saveManager.addState(hangmanState1);


        hangmanActivityController = new HangmanActivityController(
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
                }, new DisplayToast(){
                    @Override
                    public void displayToast(Context context, String text) { }
                }
        );




//        accountManager = new AccountManager(new ArrayList<Account>());
//        fileSystem.saveAccount(context, accountManager);
//        hangmanActivityController = new HangmanActivityController(fileSystem, displayToast);





    }


    @Test
    public void testUpdateGameListener(){

        setUp();
        sixGuesses();
        saveManager = user.getCurrentSaveManager(Account.hangmanName);
        Context thisContext = new AppCompatActivity();
        hangmanActivityController.updateGameListener(thisContext);
        assertEquals("Your score is 0 !", LoosingActivity.getScoreMessage());

    }

    @Test
    public void testSaveListener(){

        setUp();
        sixGuesses();
        hangmanActivity = new HangmanActivity();
        fileSystem = new FileSystem();
        saveManager = user.getCurrentSaveManager(Account.hangmanName);
        Account user1 = new Account("hello", "parrot");
        Account user2 = new Account("hi", "macaw");
        List<Account> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        hangmanActivityController.accountManager.addUser(user1);
        hangmanActivityController.accountManager.addUser(user2);

        hangmanActivityController.saveListener(hangmanActivity);

        accountManager = hangmanActivityController.fileSystem.loadAccount(hangmanActivity);

        assertEquals(hangmanActivityController.accountManager, accountManager);
    }
}

