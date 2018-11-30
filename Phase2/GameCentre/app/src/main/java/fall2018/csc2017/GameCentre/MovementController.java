/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

class MovementController {
    /**
     * BoardManager object
     */
    private BoardManager boardManager = null;

    /**
     * FileSystem object
     */
    private FileSystem fileSystem;

    MovementController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    BoardManager getBoardManager() {return this.boardManager;}

    Boolean processTapMovement(Context context, int position) {
        AccountManager accountManager = fileSystem.loadAccount(context);
        Account account = accountManager.findUser(StartingLoginActivity.currentUser);
        if(!Account.hangmanName.equals(account.getLastPlayedGame())){
            if (boardManager.isValidTap(position)) {
                boardManager.touchMove(position);
                return true;
            } else {
                return false; } }
        return null; }}
