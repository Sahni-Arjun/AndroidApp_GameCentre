/*
View class.
 */
package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * The Hangman scoreboard activity.
 */
public class HangmanScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_score);
        HangmanScoreActivityController hangmanScoreActivityController = new HangmanScoreActivityController(new FileSystem());
        StringBuilder hangmanScoreboard = hangmanScoreActivityController.onCreateListener(this);
        TextView hangmanScores = findViewById(R.id.hangmanScores);
        hangmanScores.setText(hangmanScoreboard);
    }
}