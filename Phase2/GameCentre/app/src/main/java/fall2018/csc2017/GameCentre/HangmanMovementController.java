package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;

class HangmanMovementController {

    private WordManager wordManager = null;

    HangmanMovementController() {
    }

    void setWordManager(WordManager wordManager) {
        this.wordManager = wordManager;
    }

    void processTapMovement(Context context, int position) {


        // todo discuss with group if taping a letter will yield any activity

        //if (wordManager.isValidTap(position)) {
        //    wordManager.touchMove(position);
        //
        //} else {
        //    Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        //}

        Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();

    }
}
