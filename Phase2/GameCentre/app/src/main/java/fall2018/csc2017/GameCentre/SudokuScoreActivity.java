package fall2018.csc2017.GameCentre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SudokuScoreActivity extends AppCompatActivity {

    /**
     * The scoreboard for the sliding tile game.
     */
    Scoreboard sudokuScoreboard;

    /**
     * The file system.
     */
    private FileSystem fileSystem = new FileSystem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tile_score);
        sudokuScoreboard = fileSystem.loadScoreboard(this, StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD);
        TextView slidingTileScores = findViewById(R.id.sldingTilesScores);
        slidingTileScores.setText(sudokuScoreboard.createTopScoreText());
    }
}
