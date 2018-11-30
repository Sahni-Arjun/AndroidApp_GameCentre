package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The activity which activates when the user wins the game.
 */
public class LoosingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loosing);
        TextView scoreTxt = findViewById(R.id.scoreTextA);
        TextView wordMissed = findViewById(R.id.word);

        String wordMessage = "The word was: " + WordManager.stringWord.toUpperCase() + " !";
        String scoreMessage = "Your score is 0 !";
        scoreTxt.setText(scoreMessage);
        wordMissed.setText(wordMessage);
        addBackButtonListener();
    }

    @Override
    public void onBackPressed() {
        switchToGameMenu();
    }

    /**
     * Activate the Back Button.
     */
    private void addBackButtonListener() {
        Button saveButton = findViewById(R.id.backButtonA);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameMenu();
            }
        });
    }

    /**
     * Switch to the HangmanComplexityActivity to start a new game.
     */
    private void switchToGameMenu() {
        Intent tmp = new Intent(this, HangmanComplexityActivity.class);
        startActivity(tmp);
    }
}
