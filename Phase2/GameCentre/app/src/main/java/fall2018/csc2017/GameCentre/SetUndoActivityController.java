package fall2018.csc2017.GameCentre;

import android.content.Context;

public class SetUndoActivityController {

    /**
     * The number of moves allowed to undo.
     */
    public static int undo;

    /**
     * If unlimited moves is allowed.
     */
    private static boolean unlimited = false;

    /**
     * If a valid number or the string "unlimited" was entered.
     */
    private boolean validValue;

    /**
     * The model that file reads and writes.
     */
    private FileSystem fileSystem;

    /**
     * Creates a new controller for the SetUndoActivity.
     * @param fileSystem the model that file reads and writes.
     */
    SetUndoActivityController(FileSystem fileSystem){
        this.fileSystem = fileSystem;
    }

    /**
     * Sets a valid undo value for the sliding tile game.
     * @param currentContext the current activity.
     * @param undoNumber the desired number of undos.
     * @return whether a game was sucessfully saved.
     */
    boolean setUndoListener(Context currentContext, String undoNumber){
        fillInSetUndo(undoNumber);
        if (validValue) {
            AccountManager accountManager = fileSystem.loadAccount(currentContext);

            Board.numCols = SlidingTileComplexityActivity.complexity;
            Board.numRows = SlidingTileComplexityActivity.complexity;
            SlidingTileStartingActivity.slidingTilesBoardManager = new SlidingTilesBoardManager();

            Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
            SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);
            currSavManager.wipeSave(SaveManager.auto);

            //Start new game with chosen number of undos
            SlidingTilesState newState = new
                    SlidingTilesState(SlidingTileStartingActivity.slidingTilesBoardManager, 0);
            newState.setComplexity(SlidingTileComplexityActivity.complexity);
            if (SetUndoActivityController.unlimited) {
                newState.setUnlimitedUndo();
            } else {
                newState.setMaxNumMovesUndone(SetUndoActivityController.undo);
            }
            currSavManager.addState(newState);
            fileSystem.saveAccount(currentContext, accountManager);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Save the undo value the User specifies.
     */
    private void fillInSetUndo(String undoNumber){
        validValue = true;
        try {
            switch (undoNumber) {
                case "unlimited":
                    SetUndoActivityController.unlimited = true;
                    break;
                case "":
                    SetUndoActivityController.undo = 0;
                    break;
                default:
                    SetUndoActivityController.undo = Integer.parseInt(undoNumber);
                    break;
            }
        }catch (NumberFormatException e){
            validValue = false;
        }
    }
}
