package fall2018.csc2017.GameCentre;

import android.content.Context;

public class WinningActivityController {
    /**
     * The scoreboard for the given game.
     */
    private Scoreboard scoreBoard;

    /**
     * The file system.
     */
    private FileSystem fileSystem = new FileSystem();

    private String filename;

    WinningActivityController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    private void findFilename(Context context){
        AccountManager accountManager = fileSystem.loadAccount(context);
        Account user = accountManager.findUser(StartingLoginActivity.currentUser);
        String lastPlayedGame = user.getLastPlayedGame();

        switch (lastPlayedGame) {
            case Account.slidingName:
                filename = StartingLoginActivity.SAVE_SLIDING_SCOREBOARD;
                break;
            case Account.hangmanName:
                filename = StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD;
                break;
            case Account.sudokuName:
                filename = StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD;
                break;
            default:
                filename = StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD;
        }
    }

    public String onOpenListener(Context context) {
        findFilename(context);
        scoreBoard = fileSystem.loadScoreboard(context, filename);
        int score = scoreBoard.getLatestScore();
        String scoreMessage = "Your Score: " + String.valueOf(score);
        return scoreMessage;
    }

}
