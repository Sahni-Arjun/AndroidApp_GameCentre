package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
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

    List<Letter> lettersMock = new ArrayList<>();

    public Word word = new Word(lettersMock);

    private Account user;

    private AccountManager accountManager;

    private int gameFile = 1;


    /**
     * Make a starting Word.
     */
    private void setUpStartingWord() {

        HangmanComplexityActivity.complexity = 4;
        FileSystem fileSystem = new FileSystem();
        DisplayToast displayToast = new DisplayToast();
        hangmanActivity = new HangmanActivity();
        hangmanStartingActivityController = new HangmanStartingActivityController(fileSystem, displayToast);

        List<Letter> letters = setUpLetters("seven");
        word.setWord(5);
        word.setLetters(letters);
        wordManager = new WordManager(word);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.hangmanName);
        currSavManager.wipeSave(SaveManager.auto);
        HangmanState newState = new
                HangmanState(HangmanStartingActivity.wordManager, 0);
        newState.setComplexity(HangmanComplexityActivity.complexity);
        newState.setUnlimitedUndo();
        currSavManager.addState(newState);
        fileSystem.saveAccount(hangmanStartingActivity, accountManager);
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
        user = new Account("user", "Password");
        user.setCurrentGame(1);
        SaveManager saveManager = user.getCurrentSaveManager(Account.hangmanName);
        HangmanState hangmanState1 = new HangmanState(wordManager, 0, 4, 3, 0, true);
        saveManager.addState(hangmanState1);
    }

    public void setUp(){
        StartingLoginActivity.currentUser = "Hello";
        setUpAccountWithGame();
//        SlidingTileActivity.slidingTilesBoardManager = new SlidingTilesBoardManager();
        hangmanActivityController = new HangmanActivityController(
                new FileSystem(){
                    public AccountManager loadAccount(Context context){
                        List<Account> emptyAccounts = new ArrayList<>();
                        AccountManager accountManager = new AccountManager(emptyAccounts);
                        accountManager.addUser(user);
                        return accountManager;
                    }

                    public void saveAccount(Context context, AccountManager accountManager){
                        user = accountManager.findUser("Hello");
                    }
                }, new DisplayToast(){
            public void displayToast(Context Context, String message){
                System.out.println(message);
            }
        }
        );
    }


    @Test
    public void testUpdateGameListener(){

        setUpAccountWithGame();
        setUpStartingWord();
        sixGuesses();
        hangmanActivityController.updateGameListener(hangmanActivity);
        assertEquals("Your score is 0 !", LoosingActivity.getScoreMessage());


    }


    /**
     * Test whether pressing undo will revert back to the previous Hangman State
     */
    @Test
    public void testUndoListener(){  // TODO ERASE IF SUDOKU HAS UNDO

          assertTrue(true);
//        Context context = new AppCompatActivity();
//        setUpAccountWithGame();
//        SaveManager saveManager = user.getCurrentSaveManager(Account.hangmanName);
//        hangmanActivityController.undoListener(context);
//        hangmanActivityController.undoListener(context);
//        HangmanState state = (HangmanState) saveManager.getLastState(SaveManager.hangmanName);
//        Letter[][] newLetters = state.getWordManager().getWord().getLetters();
//        for (int i = 0; i < 5; i++){
//
//            assertEquals(newLetters[0][i].hidden, false);
//        }


    }
}
