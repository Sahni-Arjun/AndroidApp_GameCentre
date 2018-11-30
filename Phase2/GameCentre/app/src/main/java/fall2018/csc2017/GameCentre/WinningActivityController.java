/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

/**
 * The controller for the activity.
 */
class WinningActivityController {

    /**
     * The file system.
     */
    private FileSystem fileSystem;

    /**
     * Creates a new controller to manage the logic of the activity.
     * @param fileSystem the file system.
     */
    WinningActivityController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Find the file name of the current game.
     * @param context the current activity.
     * @return the serializable file of the current game.
     */
    String findFilename(Context context) {
        AccountManager accountManager = fileSystem.loadAccount(context);
        Account user = accountManager.findUser(StartingLoginActivity.currentUser);
        String lastPlayedGame = user.getLastPlayedGame();

        switch (lastPlayedGame) {
            case Account.slidingName:
                return StartingLoginActivity.SAVE_SLIDING_SCOREBOARD;
            case Account.hangmanName:
                return StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD;
            case Account.sudokuName:
                return StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD;
            default:
                return StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD;
        }
    }

    /**
     * Display the scores.
     * @param context the current activity.
     * @return the scores.
     */
    String onOpenListener(Context context) {
        String filename = findFilename(context);
        Scoreboard scoreBoard = fileSystem.loadScoreboard(context, filename);
        int score = scoreBoard.getLatestScore();
        return "Your Score: " + String.valueOf(score);
    }
}
