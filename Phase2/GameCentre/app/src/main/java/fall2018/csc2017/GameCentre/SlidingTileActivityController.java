/*
Controller class.
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;

class SlidingTileActivityController {
    /**
     * The filesystem.
     */
    private FileSystem fileSystem;

    /**
     * The account manager for the app.
     */
    private AccountManager accountManager;

    SlidingTileActivityController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    SlidingTilesBoardManager onCreateListener(Context context) {
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);
        String continueOrLoad = currSavManager.getContinueOrLoad();
        return ((SlidingTilesState) currSavManager.getLastState(continueOrLoad)).getSlidingTilesBoardManager();
    }

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

    void saveListener(Context context) {
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.getCurrentSaveManager(Account.slidingName).updateSave(SaveManager.perma);

        fileSystem.saveAccount(context, accountManager);
    }
    void createTileButtons(Context context, SlidingTilesBoardManager manager, ArrayList<Button> buttons) {
        Board board = manager.getBoard();
        buttons = new ArrayList<>();
        for (int row = 0; row != Board.numRows; row++) {
            for (int col = 0; col != Board.numCols; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                buttons.add(tmp);
            }
        }
    }

}
