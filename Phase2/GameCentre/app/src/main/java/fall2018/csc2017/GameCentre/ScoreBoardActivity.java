/*
View
 */
package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The activity for the menu of scoreboards.
 */
public class ScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        addSlidingTileScoreButtonListener();
        addSudokuScoreButtonListener();
    }

    /**
     * Activate sliding tile scoreboard button.
     */
    private void addSlidingTileScoreButtonListener() {
        Button button = findViewById(R.id.buttonSlidingTileScore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingTilesScore();
            }
        });
    }

    /**
     * Activate sliding tile scoreboard button.
     */
    private void addSudokuScoreButtonListener() {
        Button button = findViewById(R.id.buttonSudokuScore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSudokuScore();
            }
        });
    }

    /**
     * Switch to sliding tile scoreboard.
     */
    private void switchToSlidingTilesScore() {
        Intent tmp = new Intent(this, SlidingTileScoreActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to sudoku scoreboard.
     */
    private void switchToSudokuScore() {
        Intent tmp = new Intent(this, SudokuScoreActivity.class);
        startActivity(tmp);
    }
}
