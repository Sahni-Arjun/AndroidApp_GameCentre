package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;

class SudokuMovementController {

    private SudokuBoardManager boardManager = null;

    SudokuMovementController() {
    }

    void setBoardManager(SudokuBoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            Toast.makeText(context, "Valid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
