package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;

class HangmanMovementController {

    private WordManager wordManager;

    void setWordManager(WordManager wordManager) {
        this.wordManager = wordManager;
    }

    void processTapMovement(Context context) {
        Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
    }
}
