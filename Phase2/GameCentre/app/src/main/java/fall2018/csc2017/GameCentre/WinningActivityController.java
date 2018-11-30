/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

class WinningActivityController {

    /**
     * The file system.
     */
    private FileSystem fileSystem;

    WinningActivityController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

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

    String onOpenListener(Context context) {
        String filename = findFilename(context);
        Scoreboard scoreBoard = fileSystem.loadScoreboard(context, filename);
        int score = scoreBoard.getLatestScore();
        return "Your Score: " + String.valueOf(score);
    }
}
