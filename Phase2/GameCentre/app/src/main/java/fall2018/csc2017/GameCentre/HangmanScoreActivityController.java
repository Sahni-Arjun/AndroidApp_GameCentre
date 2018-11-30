package fall2018.csc2017.GameCentre;

import android.content.Context;

/**
 * The controller for the Hangman Score Activity.
 */
public class HangmanScoreActivityController {

    public StringBuilder onCreateListener(Context context) {
        Scoreboard hangmanScoreboard;
        FileSystem fileSystem = new FileSystem();
        hangmanScoreboard = fileSystem.loadScoreboard(context, StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD);
        return hangmanScoreboard.createTopScoreText();
    }
}
