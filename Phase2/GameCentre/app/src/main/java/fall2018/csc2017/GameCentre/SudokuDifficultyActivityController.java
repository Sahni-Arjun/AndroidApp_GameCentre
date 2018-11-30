package fall2018.csc2017.GameCentre;

class SudokuDifficultyActivityController {
    /**
     * The account manager.
     */
    private AccountManager accountManager;

    /**
     * the boardmanager
     */
    private SudokuBoardManager boardManager;

    /**
     * The filesystem.
     */
    private FileSystem fileSystem;


    SudokuDifficultyActivityController(){
        this.fileSystem = new FileSystem();
    }


    /**
     * starts the sudoku game
     * @param context
     */
    void startGame(SudokuDifficultyActivity context, int difficulty) {

        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(SaveManager.sudokuName);
        currSavManager.wipeSave(SaveManager.auto);
        boardManager = new SudokuBoardManager();
        //Start new game with chosen number of undos
        SudokuState newState = new
                SudokuState(boardManager, 0);

        newState.setDifficulty(difficulty);

        newState.setUnlimitedUndo();

        currSavManager.addState(newState);
        fileSystem.saveAccount(context, accountManager);
    }

}
