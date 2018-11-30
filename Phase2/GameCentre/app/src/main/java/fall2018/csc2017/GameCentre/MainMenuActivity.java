/*
View
 */
package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * The activity for the main menu.
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        addScoreBoardButtonListener();
        addChooseGameButtonListener();
        addLogOutButtonListener();
    }

    @Override
    public void onBackPressed() {
        invalidBackPressed();
    }

    /**
     * Make it so that the user cannot enter the back button on this activity.
     */
    public void invalidBackPressed() {
        Toast.makeText(getApplicationContext(), "Cannot press back button!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the scoreboard button.
     */
    private void addScoreBoardButtonListener() {
        Button button = findViewById(R.id.ScoreBoard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreBoard();
            }
        });
    }

    /**
     * Switch to the scoreboard.
     */
    private void switchToScoreBoard() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the choose game button.
     */
    private void addChooseGameButtonListener() {
        Button button = findViewById(R.id.ChooseGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToChooseGameMenu();
            }
        });
    }

    /**
     * Switch to the choose game menu.
     */
    private void switchToChooseGameMenu() {
        Intent tmp = new Intent(this, ChooseGameMenuActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the logout button.
     */
    private void addLogOutButtonListener() {
        Button button = findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToStartingLoginActivity();
            }
        });
    }

    /**
     * Switch to starting login activity.
     */
    private void switchToStartingLoginActivity() {
        Intent tmp = new Intent(this, StartingLoginActivity.class);
        startActivity(tmp);
    }
}
