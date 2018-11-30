/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

public class SudokuScoreActivityController {
    private FileSystem fileSystem;

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
