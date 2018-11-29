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
     * @param fileSystem the filesystem for this controller.
     * @param displayToast the toast displayer.
     */
    HangmanStartingActivityController(FileSystem fileSystem, DisplayToast displayToast) {
        this.fileSystem = fileSystem;
        this.displayToast = displayToast;
    }


    /**
     * The logic that must be processed before the new game is created.
     *
     * @param context the context for the activity
     * @param gameFile the location of the new game
     *
     */
    void newGame(int gameFile, Context context) {

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
            int wordNum = rand.nextInt(840);
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

        currentAccount.setCurrentGame(gameFile);


        SaveManager currSavManager = currentAccount.getSaveManager();
        currSavManager.wipeSave(SaveManager.auto, SaveManager.hangmanName);

        //Start new game with chosen number of undoes // todo discuss with group
        HangmanState newState = new
                HangmanState(HangmanStartingActivity.wordManager, 0);
        newState.setComplexity(HangmanComplexityActivity.complexity);
        newState.setUnlimitedUndo(); // todo discuss with group
        currSavManager.addState(newState, SaveManager.hangmanName);

        fileSystem.saveAccount(context, accountManager);

        ((HangmanStartingActivity) context).switchToHangman();

    }



     /**
     * The method that loads saved files and logic that must be processed before the game is loaded.
     * @param gameFile the number of the game file the user would like to open
     * @param context the current HangmanStartingActivity
     */
    void loadSave(int gameFile, Context context) {
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getSaveManager();

        if (currSavManager.getLength("perma", SaveManager.hangmanName) != 0) {
            HangmanStartingActivity.wordManager = ((HangmanState) currSavManager.getLastState("perma", SaveManager.hangmanName)).getWordManager();
            currSavManager.updateSave("auto", SaveManager.hangmanName);
            HangmanState prePermaState = (HangmanState) currSavManager.getLastState("perma", SaveManager.hangmanName);
            HangmanComplexityActivity.complexity = prePermaState.getComplexity();
            displayToast.displayToast(context, "Loaded Game");
            fileSystem.saveAccount(context, accountManager);
            ((HangmanStartingActivity) context).switchToHangman();
        } else {
            displayToast.displayToast(context.getApplicationContext(),"you can't continue a game " +
                    "that hasn't started!");
        }
    }


    /**
     * Load the continue
     * @param gameFile the number of the game file the user would like to open
     * @param currentContext the current HangmanStartingActivity
     */
    void continueSave(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.hangmanName);

        if (currSavManager.getLength(SaveManager.auto, SaveManager.hangmanName) != 0) {
            currSavManager.setContinueOrLoad(SaveManager.auto);
            HangmanState lastAutoState = (HangmanState) currSavManager.getLastState(SaveManager.auto, SaveManager.hangmanName);
            HangmanComplexityActivity.complexity = lastAutoState.getComplexity();
            Board.numRows = HangmanComplexityActivity.complexity;
            Board.numCols = HangmanComplexityActivity.complexity;
            displayToast.displayToast(currentContext, "Loaded Game " + gameFile);
            ((HangmanStartingActivity) currentContext).switchToHangman();
        } else {
            displayToast.displayToast(currentContext, "you can't continue a game " +
                    "that hasn't started!");
        }
    }












    
}
