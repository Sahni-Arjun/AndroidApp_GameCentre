package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;

public class SudokuDifficultyActivityController {
    /**
     * The account manager.
     */
    private AccountManager accountManager;

    /**
     * the boardmanager
     */
    public SudokuBoardManager boardManager;

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
    void startGame(SudokuDifficultyActivity context) {

        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getSaveManager();
        currSavManager.wipeSave(SaveManager.auto, SaveManager.sudokuName);

        //Start new game with chosen number of undos
        SudokuState newState = new
                SudokuState(boardManager, 0);

        //TODO set correct difficulty when implemented.


        //TODO set number of undos when implemented.
        newState.setUnlimitedUndo();

//                if (SetUndoActivity.unlimited) {
//                    newState.setUnlimitedUndo();
//                } else {
//                    newState.setMaxNumMovesUndone(SetUndoActivity.undo);
//                }
        currSavManager.addState(newState, SaveManager.sudokuName);
        fileSystem.saveAccount(context, accountManager);
        context.switchToSudoku();
    }

}
