/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

class SudokuActivityController {
    /**
     * The filesystem.
     */
    private FileSystem fileSystem;

    /**
     * The account manager for the app.
     */
    private AccountManager accountManager;

    /**
     * The toast view class.
     */
    private DisplayToast displayToast;

    public long getStartTime() {
        return startTime;
    }

    private long startTime;

    SudokuActivityController(FileSystem fileSystem, DisplayToast displayToast){
        this.fileSystem = fileSystem;
        this. displayToast = displayToast;
    }

    /**
     * Get the last auto save state
     * @param context Context
     * @return SudokuBoardManager
     */
    SudokuBoardManager onCreateListener(Context context){
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.sudokuName);
        String continueOrLoad = currSavManager.getContinueOrLoad(); //"auto"
        startTime = System.currentTimeMillis();
        return ((SudokuState)currSavManager.getLastState(continueOrLoad)).getBoardManager();
    }

    void onPauseListener(Context context){
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(SaveManager.sudokuName);
        if (currSavManager.getLength(SaveManager.auto) != 0) {
            SudokuState lastAutoState = (SudokuState) currSavManager.getLastState("auto");
            long lastTime = lastAutoState.getTime();
            long newTime = lastTime + System.currentTimeMillis() - startTime;
            lastAutoState.setTime(newTime);
            fileSystem.saveAccount(context, accountManager);
        }
    }

    void onResumeListener(){
        startTime = System.currentTimeMillis();
    }

    boolean updateListener(Context context, SudokuBoardManager boardManager){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(SaveManager.sudokuName);

        currSavManager.updateState(SaveManager.sudokuName, boardManager);

        currSavManager.updateSudokuTime(startTime);
        fileSystem.saveAccount(context, accountManager);
        startTime = System.currentTimeMillis();

        SudokuState prevState = (SudokuState) currSavManager.getLastState(SaveManager.auto);
        SudokuBoardManager sudokuBoardManager = prevState.getBoardManager();

        //Saving/Displaying the score if the game is over.
        if (sudokuBoardManager.puzzleSolved()) {
            Scoreboard scoreBoard = fileSystem.loadScoreboard(context,StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD);
            scoreBoard.addToScoreBoard(scoreBoard.createScore(StartingLoginActivity.currentUser,
                    currSavManager.getFinalScore(SaveManager.sudokuName)));
            fileSystem.saveScoreBoard(context, StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD, scoreBoard);
            accountManager.findUser(StartingLoginActivity.currentUser).setLastPlayedGame(Account.sudokuName);
            currSavManager.wipeSave(SaveManager.auto);
            currSavManager.wipeSave(SaveManager.perma);
            fileSystem.saveAccount(context, accountManager);
            return true;
        }
        return false;
    }

    boolean undoListener(Context context, SudokuBoardManager boardManager){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(SaveManager.sudokuName);

        boolean canUndo = currSavManager.getLastState("auto").canUndo();
        SudokuState currentAutoState = (SudokuState) currSavManager.getLastState("auto");

        if ((currSavManager.getLength("auto") != 1) && canUndo) {
            int prevMovesUndone = currentAutoState.getNumMovesUndone();
            currSavManager.undo();
            SudokuState prevState;
            prevState = (SudokuState) currSavManager.getLastState("auto");

            Tile[][] prevTiles = prevState.getBoardManager().getBoard().getTiles();
            boardManager.getBoard().setTiles(prevTiles);
            currSavManager.getLastState("auto").incrementNumMoves(prevMovesUndone);
            fileSystem.saveAccount(context, accountManager);
            return true;
        } else {
            return false;
        }
    }

    void saveListener(Context context){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.getCurrentSaveManager(SaveManager.sudokuName).updateSave("perma");

        fileSystem.saveAccount(context, accountManager);
    }

}
