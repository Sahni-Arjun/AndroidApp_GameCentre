/*
View
 */
package fall2018.csc2017.GameCentre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Displays the scores of sudoku.
 */
public class SudokuScoreActivity extends AppCompatActivity {

    /**
     * The file system.
     */
    private FileSystem fileSystem = new FileSystem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tile_score);
        SudokuScoreActivityController controller = new SudokuScoreActivityController(fileSystem);
        StringBuilder sudokuScoreboard = controller.onCreateListener(this);
        TextView slidingTileScores = findViewById(R.id.sldingTilesScores);
        slidingTileScores.setText(sudokuScoreboard);
    }
}
