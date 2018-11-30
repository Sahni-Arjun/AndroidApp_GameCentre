/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

/**
 * The controller for the activity.
 */
public class SudokuScoreActivityController {
    /**
     * The file system.
     */
    private FileSystem fileSystem;

    /**
     * Creates a new controller for the logic in the activity.
     * @param fileSystem the file system.
     */
    SudokuScoreActivityController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Listens for an onCreate call in the activity to return the top scores in Sudoku.
     *
     * @param context the current Activity.
     * @return StringBuilder that will be displayed to the user.
     */
    public StringBuilder onCreateListener(Context context) {
        Scoreboard sudokuScoreboard;
        sudokuScoreboard = fileSystem.loadScoreboard(context, StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD);
        return sudokuScoreboard.createTopScoreText();
    }
}
