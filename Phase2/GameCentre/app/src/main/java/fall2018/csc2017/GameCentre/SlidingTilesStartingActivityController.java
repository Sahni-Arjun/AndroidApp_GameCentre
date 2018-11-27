package fall2018.csc2017.GameCentre;

import android.content.Context;

/**
 * Controller class for SlidingTilesStartingActivity
 */
class SlidingTilesStartingActivityController {

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
     * @param fileSystem
     * @param displayToast
     */
    SlidingTilesStartingActivityController(FileSystem fileSystem, DisplayToast displayToast) {
        this.fileSystem = fileSystem;
        this.displayToast = displayToast;
    }

    /**
     * The method that loads saved files
     * @param gameFile the number of the game file the user would like to open
     * @param currentContext the current SlidingTileStartingActivity
     */
    void loadSave(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        if (currSavManager.getLength(SaveManager.perma, SaveManager.slidingTilesName) != 0) {
            SlidingTileStartingActivity.slidingTilesBoardManager = ((SlidingTilesState) currSavManager.getLastState(SaveManager.perma, SaveManager.slidingTilesName)).getSlidingTilesBoardManager();
            currSavManager.updateSave(SaveManager.auto, SaveManager.slidingTilesName);
            SlidingTilesState prePermaState = (SlidingTilesState) currSavManager.getLastState(SaveManager.perma, SaveManager.slidingTilesName);
            SlidingTileComplexityActivity.complexity = prePermaState.getComplexity();
            Board.numRows = SlidingTileComplexityActivity.complexity;
            Board.numCols = SlidingTileComplexityActivity.complexity;
            displayToast.displayToast(currentContext, "Loaded Game " + gameFile);
            fileSystem.saveAccount(currentContext, accountManager);
            ((SlidingTileStartingActivity) currentContext).switchToGame();
        } else {
            displayToast.displayToast(currentContext, "Use common sense you can't" +
                    " load if you haven't saved yet!");
        }
    }

    /**
     * Load the continue
     * @param gameFile
     * @param currentContext
     */
    void continueSave(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        if (currSavManager.getLength(SaveManager.auto, SaveManager.slidingTilesName) != 0) {
            SlidingTileStartingActivity.slidingTilesBoardManager = ((SlidingTilesState) currSavManager.getLastState(SaveManager.auto, SaveManager.slidingTilesName)).getSlidingTilesBoardManager();
            SlidingTilesState lastAutoState = (SlidingTilesState) currSavManager.getLastState(SaveManager.auto, SaveManager.slidingTilesName);
            SlidingTileComplexityActivity.complexity = lastAutoState.getComplexity();
            Board.numRows = SlidingTileComplexityActivity.complexity;
            Board.numCols = SlidingTileComplexityActivity.complexity;
            displayToast.displayToast(currentContext, "Loaded Game " + gameFile);
            ((SlidingTileStartingActivity) currentContext).switchToGame();
        } else {
            displayToast.displayToast(currentContext, "you can't continue a game " +
                    "that hasn't started!");
        }
    }

    /**
     * Load new game
     * @param gameFile
     * @param currentContext
     */
    void newGame(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        fileSystem.saveAccount(currentContext, accountManager);
        ((SlidingTileStartingActivity) currentContext).switchToTileComplexity();
    }
}
