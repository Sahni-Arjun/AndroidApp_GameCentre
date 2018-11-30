/*
Controller
 */
package fall2018.csc2017.GameCentre;

/**
 * The controller for the activity.
 */
class SudokuDifficultyActivityController {

    /**
     * The filesystem.
     */
    private FileSystem fileSystem;


    /**
     * Creates a new controller that manages the logic in the activity.
     */
    SudokuDifficultyActivityController(){
        this.fileSystem = new FileSystem();
    }


    /**
     * starts the sudoku game
     * @param context SudokuDifficultyActivity
     */
    void startGame(SudokuDifficultyActivity context, int difficulty) {

        AccountManager accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(SaveManager.sudokuName);
        currSavManager.wipeSave(SaveManager.auto);
        SudokuBoardManager boardManager = new SudokuBoardManager();
        //Start new game with chosen number of undos
        SudokuState newState = new
                SudokuState(boardManager, 0);

        newState.setDifficulty(difficulty);

        newState.setUnlimitedUndo();

        currSavManager.addState(newState);
        fileSystem.saveAccount(context, accountManager);
    }

}
