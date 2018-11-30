/*
View
 */
package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * The activity which chooses the complexity of the hangman game.
 */
public class HangmanComplexityActivity extends AppCompatActivity {

    /**
     * The complexity of the game.
     */
    public static Integer complexity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_complexity);
        addButtonEasyListener();
        addButtonMediumListener();
        addButtonHardListener();
    }

    @Override
    public void onBackPressed() {
        switchToChooseGame();
    }

    /**
     * Switch to Choose Game Activity.
     */
    private void switchToChooseGame() {
        Intent chooseGameActivity = new Intent(this, ChooseGameMenuActivity.class);
        startActivity(chooseGameActivity);
    }

    /**
     * Switch to Hangman Starting Activity.
     */
    private void switchToHangmanGame() {
        Intent hangmanStartingActivity = new Intent(this, HangmanStartingActivity.class);
        startActivity(hangmanStartingActivity);
    }

    /**
     * Activate button to set complexity to 3 (easy).
     */
    private void addButtonEasyListener() {
        Button buttonEasy = findViewById(R.id.buttoncomplexityeasy);
        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {complexity = 3; switchToHangmanGame();
            }
        });

    }

    /**
     * Activate button to set complexity to 4 (medium).
     */
    private void addButtonMediumListener() {
        Button buttonMedium = findViewById(R.id.buttoncomplexitymedium);
        buttonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {complexity = 4; switchToHangmanGame();
            }
        });
    }

    /**
     * Activate button to set complexity to 5 (hard).
     */
    private void addButtonHardListener() {
        Button buttonHard = findViewById(R.id.buttoncomplexityhard);
        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {complexity = 5; switchToHangmanGame();
            }
        });
    }
}
