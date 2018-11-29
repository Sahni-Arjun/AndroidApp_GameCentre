package fall2018.csc2017.GameCentre;

import android.content.Context;

public class SudokuScoreActivityController {
    public StringBuilder onCreateListener(Context context) {
        Scoreboard sudokuScoreboard;
        FileSystem fileSystem = new FileSystem();
        sudokuScoreboard = fileSystem.loadScoreboard(context, StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD);
        return sudokuScoreboard.createTopScoreText();
    }
}
