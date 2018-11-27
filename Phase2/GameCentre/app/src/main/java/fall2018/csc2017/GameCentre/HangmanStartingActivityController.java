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
     * The filesystem for this class.
     */
    private FileSystem fileSystem;

    /**
     * The toast view class.
     */
    private DisplayToast displayToast;

    /**
     * The account manager for this class.
     */
    private AccountManager accountManager;

    /**
     * Initializes the fileSystem for the controller.
     *
     * @param fileSystem the fileSystem
     */
    HangmanStartingActivityController(FileSystem fileSystem, DisplayToast displayToast) {
        this.fileSystem = fileSystem;
        this.displayToast = displayToast;
    }

    /**
     * The logic that must be processed before the new game is created.
     *
     * @param context the context for the activity
     */
    void newGameButtonListener(Context context) {
        accountManager = fileSystem.loadAccount(context);
        Word.numCols = HangmanComplexityActivity.complexity + 1;
        // numRows must remain 1 or made deprecated:
        Word.numRows = 1;

        String[] words;
        String selectedWord ="";

        try {
            InputStream is = context.getAssets().open("words.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            words = text.split("\\r?\\n");
            Random rand = new Random();
            int wordNum = rand.nextInt(900);
            selectedWord = words[wordNum];
            while(selectedWord.length() != (HangmanComplexityActivity.complexity + 1) ) {
                rand = new Random();
                wordNum = rand.nextInt(800);
                selectedWord = words[wordNum];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HangmanStartingActivity.wordManager = new WordManager(selectedWord);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getSaveManager();
        currSavManager.wipeSave(SaveManager.auto, SaveManager.hangmanName);

        //Start new game with chosen number of undoes // todo discuss with group
        HangmanState newState = new
                HangmanState(HangmanStartingActivity.wordManager, 0);

        newState.setComplexity(HangmanComplexityActivity.complexity);

        newState.setUnlimitedUndo(); // todo discuss with group

        currSavManager.addState(newState, SaveManager.hangmanName);
        fileSystem.saveAccount(context, accountManager);
    }

    /**
     * The logic that must be processed before the game is loaded.
     *
     * @param context the context for the activity
     */
    boolean loadButtonListener(Context context) {
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getSaveManager();

        if (currSavManager.getLength("perma", SaveManager.hangmanName) != 0) {
            HangmanStartingActivity.wordManager = ((HangmanState) currSavManager.getLastState("perma", SaveManager.hangmanName)).getWordManager();
            currSavManager.updateSave("auto", SaveManager.hangmanName);
            HangmanState prePermaState = (HangmanState) currSavManager.getLastState("perma", SaveManager.hangmanName);
            HangmanComplexityActivity.complexity = prePermaState.getComplexity();
            displayToast.displayToast(context, "Loaded Game");
            fileSystem.saveAccount(context, accountManager);
        } else {
            displayToast.displayToast(context.getApplicationContext(),"you can't continue a game " +
                    "that hasn't started!");
        }
        return currSavManager.getLength("perma", SaveManager.hangmanName) != 0;
    }

    /**
     * The logic that must be processed before the game is continued.
     * @param context the context for the activity
     */
    boolean  continueButtonListener(Context context) {
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getSaveManager();

        if (currSavManager.getLength("auto", SaveManager.hangmanName) != 0) {
            HangmanStartingActivity.wordManager = ((HangmanState) currSavManager.getLastState("auto", SaveManager.hangmanName)).getWordManager();
            HangmanState lastAutoState = (HangmanState) currSavManager.getLastState("auto", SaveManager.hangmanName);
            HangmanComplexityActivity.complexity = lastAutoState.getComplexity();
            Word.numRows = 1;
            Word.numCols = HangmanComplexityActivity.complexity + 1; // todo: discuss if we shall relate complexity to length or to word content
            displayToast.displayToast(context, "Loaded Game");
        } else {
            displayToast.displayToast(context.getApplicationContext(),"you can't continue a game " +
                    "that hasn't started!");
        }
        return currSavManager.getLength("auto", SaveManager.hangmanName) != 0;
    }
}
