package fall2018.csc2017.GameCentre;

import android.content.Context;

/**
 * The controller for the Hangman Score Activity.
 */
public class HangmanScoreActivityController {

    /**
     * The fileSystem used for this controller.
     */
    private FileSystem fileSystem;

    HangmanScoreActivityController(FileSystem fileSystem){
        this.fileSystem = fileSystem;
    }

    public StringBuilder onCreateListener(Context context) {
        Scoreboard hangmanScoreboard;
        hangmanScoreboard = fileSystem.loadScoreboard(context, StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD);
        return hangmanScoreboard.createTopScoreText();
    }
}
