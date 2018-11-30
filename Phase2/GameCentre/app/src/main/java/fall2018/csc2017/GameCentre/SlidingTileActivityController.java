/*
Controller class.
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

class SlidingTileActivityController extends NotRobotActivityController {
    /**
     * The filesystem.
     */
    private FileSystem fileSystem;

    /**
     * The account manager for the app.
     */
    private AccountManager accountManager;

    /**
     * Creates a new controller that manages the logic of the activity.
     * @param fileSystem the file system.
     */
    SlidingTileActivityController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Returns the last saved state of the game.
     * @param context the current activity.
     * @return the last saved state of the game.
     */
    SlidingTilesBoardManager onCreateListener(Context context) {
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);
        currentAccount.setLastPlayedGame(Account.slidingName);
        String continueOrLoad = currSavManager.getContinueOrLoad();
        return ((SlidingTilesState) currSavManager.getLastState(continueOrLoad)).getSlidingTilesBoardManager();
    }

    /**
     * Saves the current game as the most recent played game.
     * @param context the current activity.
     */
    void onResumeListener(Context context){
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setLastPlayedGame(Account.slidingName);
        fileSystem.saveAccount(context, accountManager);
    }

    /**
     * If allowed undos the game to the previous saved state.
     * @param context the current activity
     * @param slidingTilesBoardManager the current board manager of the game.
     * @return if an undo was successful.
     */
    boolean undoListener(Context context, SlidingTilesBoardManager slidingTilesBoardManager) {
        // load the account manager.
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        boolean undone = currSavManager.undoMove("sliding tiles");
        if (undone) {
            // update the current board manager with the new tiles.
            slidingTilesBoardManager.getBoard().setTiles(currSavManager.getboardArrangement());
            fileSystem.saveAccount(context, accountManager);
            return false;
        }
        return true;
    }

    /**
     * Updates the display of the screen if the board arrangement has cahnged.
     * @param context the current activity.
     * @param boardManager the current board manager of the game.
     * @return whether the game has ended.
     */
    boolean updateGameListener(Context context, SlidingTilesBoardManager boardManager) {
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        currSavManager.updateState(SaveManager.slidingTilesName, boardManager);

        fileSystem.saveAccount(context, accountManager);

        SlidingTilesState prevState = (SlidingTilesState) currSavManager.getLastState(SaveManager.auto);
        SlidingTilesBoardManager slidingTilesBoardManager = prevState.getSlidingTilesBoardManager();
        //Saving/Displaying the score if the game is over.
        if (slidingTilesBoardManager.puzzleSolved()) {
            Scoreboard scoreboard = fileSystem.loadScoreboard(context, StartingLoginActivity.SAVE_SLIDING_SCOREBOARD);

            scoreboard.addToScoreBoard(scoreboard.createScore(StartingLoginActivity.currentUser,
                    currSavManager.getFinalScore(SaveManager.slidingTilesName)));

            fileSystem.saveScoreBoard(context, StartingLoginActivity.SAVE_SLIDING_SCOREBOARD, scoreboard);

            accountManager.findUser(StartingLoginActivity.currentUser).setLastPlayedGame(Account.slidingName);
            currSavManager.wipeSave(SaveManager.auto);
            currSavManager.wipeSave(SaveManager.perma);

            fileSystem.saveAccount(context, accountManager);
            return true;
        }
        return false;
    }

    /**
     * Save the current instance of the game.
     * @param context the current activity.
     */
    void saveListener(Context context) {
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.getCurrentSaveManager(Account.slidingName).updateSave(SaveManager.perma);

        fileSystem.saveAccount(context, accountManager);
    }


}
