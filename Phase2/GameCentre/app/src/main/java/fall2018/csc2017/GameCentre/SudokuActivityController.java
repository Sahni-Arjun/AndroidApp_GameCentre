package fall2018.csc2017.GameCentre;

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

    private long startTime;

    SudokuActivityController(FileSystem fileSystem, DisplayToast displayToast){
        this.fileSystem = fileSystem;
        this. displayToast = displayToast;
    }

    SudokuBoardManager onCreateListener(SudokuActivity context){
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.sudokuName);
        String continueOrLoad = currSavManager.getContinueOrLoad();
        startTime = System.currentTimeMillis();
        return ((SudokuState)currSavManager.getLastState(continueOrLoad, SaveManager.sudokuName)).getBoardManager();
    }

    void onPauseListener(SudokuActivity context){
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(SaveManager.sudokuName);
        if (currSavManager.getLength(SaveManager.auto, SaveManager.sudokuName) != 0) {
            SudokuState lastAutoState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);
            long lastTime = lastAutoState.getTime();
            long newTime = lastTime + System.currentTimeMillis() - startTime;
            lastAutoState.setTime(newTime);
            fileSystem.saveAccount(context, accountManager);
        }
    }

    void onResumeListener(){
        startTime = System.currentTimeMillis();
    }

    boolean updateListener(SudokuActivity context, SudokuBoardManager boardManager){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(SaveManager.sudokuName);

        currSavManager.updateState(SaveManager.sudokuName, boardManager);

        currSavManager.updateSudokuTime(startTime);
        fileSystem.saveAccount(context, accountManager);
        startTime = System.currentTimeMillis();

        SudokuState prevState = (SudokuState) currSavManager.getLastState(SaveManager.auto, SaveManager.sudokuName);
        SudokuBoardManager sudokuBoardManager = prevState.getBoardManager();
        //Saving/Displaying the score if the game is over.
        if (sudokuBoardManager.puzzleSolved()) {
            Scoreboard scoreBoard = fileSystem.loadScoreboard(context,StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD);
            scoreBoard.addToScoreBoard(scoreBoard.createScore(StartingLoginActivity.currentUser,
                    prevState.getScore()));
            fileSystem.saveScoreBoard(context, StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD, scoreBoard);
            accountManager.findUser(StartingLoginActivity.currentUser).setLastPlayedGame(Account.sudokuName);
            currSavManager.wipeSave(SaveManager.auto, SaveManager.sudokuName);
            currSavManager.wipeSave(SaveManager.perma, SaveManager.sudokuName);
            fileSystem.saveAccount(context, accountManager);
            return true;
        }
        return false;
    }

    boolean undoListener(SudokuActivity context, SudokuBoardManager boardManager){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(SaveManager.sudokuName);

        boolean canUndo = currSavManager.getLastState("auto", SaveManager.sudokuName).canUndo();
        SudokuState currentAutoState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);

        if ((currSavManager.getLength("auto", SaveManager.sudokuName) != 1) && canUndo) {
            int prevMovesUndone = currentAutoState.getNumMovesUndone();
            currSavManager.undo(SaveManager.sudokuName);
            SudokuState prevState;
            prevState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);

            Tile[][] prevTiles = prevState.getBoardManager().getBoard().getTiles();
            boardManager.getBoard().setTiles(prevTiles);
            currSavManager.getLastState("auto", SaveManager.sudokuName).incrementNumMoves(prevMovesUndone);
            fileSystem.saveAccount(context, accountManager);
            return true;
        } else {
            displayToast.displayToast(context,"Max moves undone");
            return false;
        }
    }

    void saveListener(SudokuActivity context){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.getCurrentSaveManager(SaveManager.sudokuName).updateSave("perma", SaveManager.sudokuName);

        fileSystem.saveAccount(context, accountManager);
    }

}