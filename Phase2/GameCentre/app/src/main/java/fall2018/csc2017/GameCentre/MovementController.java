package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;

class MovementController {

    private BoardManager boardManager = null;

    MovementController() {
    }

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position) {

        FileSystem fileSystem = new FileSystem();
        AccountManager accountManager = fileSystem.loadAccount(context);
        Account account = accountManager.findUser(StartingLoginActivity.currentUser);


        if(!Account.hangmanName.equals(account.getLastPlayedGame())){

            if (boardManager.isValidTap(position)) {
                boardManager.touchMove(position);
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
