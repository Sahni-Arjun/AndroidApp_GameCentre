/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * The controller for the HangmanStartingActivity.
 */
class HangmanStartingActivityController {

    /**
     * FileSystem field
     */
    private FileSystem fileSystem;

    /**
     * DisplayToast object for displaying toast
     */
    private DisplayToast displayToast;

    /**
     * AccountManager object
     */
    private AccountManager accountManager;

    /**
     * Constructor
     *
     * @param fileSystem   the filesystem for this controller.
     * @param displayToast the toast display
     */
    HangmanStartingActivityController(FileSystem fileSystem, DisplayToast displayToast) {
        this.fileSystem = fileSystem;
        this.displayToast = displayToast;
    }

    /**
     * Select a word from a text file for the given context.
     *
     * @param context the context
     * @return a randomly selected word
     */
    private String selectWordFromFile(Context context) {
        String[] words;
        String selectedWord = "";
        final String TEXT_FILE = "words.txt";

        try {
            InputStream is = context.getAssets().open(TEXT_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            if (is.read(buffer) != -1) {
                is.close();
                String text = new String(buffer);
                words = text.split("\\r?\\n");
                Random rand = new Random();
                int wordNum = rand.nextInt(840);
                selectedWord = words[wordNum];
                while (selectedWord.length() != (HangmanComplexityActivity.complexity + 1)) {
                    rand = new Random();
                    wordNum = rand.nextInt(800);
                    selectedWord = words[wordNum];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selectedWord;
    }

    /**
     * The logic that must be processed before the new game is created.
     *
     * @param context  the context for the activity
     * @param gameFile the location of the new game
     */
    void newGame(int gameFile, Context context) {

        accountManager = fileSystem.loadAccount(context);

        Word.numCols = HangmanComplexityActivity.complexity + 1;
        Word.numRows = 1;
        WordManager.tries = 0;

        HangmanStartingActivity.wordManager = new WordManager(this.selectWordFromFile(context));

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);

        currentAccount.setCurrentGame(gameFile);

        currentAccount.setLastPlayedGame(Account.hangmanName);


        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.hangmanName);
        currSavManager.wipeSave(SaveManager.auto);

        //Start new game with chosen number of undoes
        HangmanState newState = new
                HangmanState(HangmanStartingActivity.wordManager, 0);
        newState.setComplexity(HangmanComplexityActivity.complexity);
        newState.setUnlimitedUndo();
        currSavManager.addState(newState);

        fileSystem.saveAccount(context, accountManager);
    }

    /**
     * The method that loads saved files and logic that must be processed before the game is loaded.
     *
     * @param gameFile the number of the game file the user would like to open
     * @param context  the current HangmanStartingActivity
     */
    boolean loadSave(int gameFile, Context context) { // adapt
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.hangmanName);

        if (currSavManager.getLength("perma") != 0) {
            HangmanStartingActivity.wordManager = ((HangmanState) currSavManager.getLastState("perma")).getWordManager();
            currSavManager.updateSave("auto");
            HangmanState prePermaState = (HangmanState) currSavManager.getLastState("perma");
            HangmanComplexityActivity.complexity = prePermaState.getComplexity();
            fileSystem.saveAccount(context, accountManager);
        }
        return currSavManager.getLength("perma") != 0;
    }

    /**
     * Load the continue
     *
     * @param gameFile       the number of the game file the user would like to open
     * @param currentContext the current HangmanStartingActivity
     */
    boolean continueSave(int gameFile, Context currentContext) {
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.hangmanName);

        if (currSavManager.getLength(SaveManager.auto) != 0) {
            currSavManager.setContinueOrLoad(SaveManager.auto);
            HangmanState lastAutoState = (HangmanState) currSavManager.getLastState(SaveManager.auto);
            HangmanComplexityActivity.complexity = lastAutoState.getComplexity();
            Board.numRows = HangmanComplexityActivity.complexity;
            Board.numCols = HangmanComplexityActivity.complexity;
        }
        return  currSavManager.getLength(SaveManager.auto) != 0;
    }
}
