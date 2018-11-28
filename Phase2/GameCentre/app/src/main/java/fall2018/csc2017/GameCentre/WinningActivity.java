package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * The activity which activates when the user wins the game.
 */
public class WinningActivity extends AppCompatActivity {

    /**
     * The scoreboard for the given game.
     */
    private Scoreboard scoreBoard;

    /**
     * The file system.
     */
    private FileSystem fileSystem = new FileSystem();

    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        findFilename();
        scoreBoard = fileSystem.loadScoreboard(this, filename);
        int score = scoreBoard.getLatestScore();
        TextView scoreTxt = findViewById(R.id.scoreText);
        String scoreMessage = "Your Score: " + String.valueOf(score);
        scoreTxt.setText(scoreMessage);
        addBackButtonListener();
    }

    private void findFilename(){
        /*
      The account manager.
     */ /**
         * The account manager.
         */AccountManager accountManager = fileSystem.loadAccount(this);
        Account user = accountManager.findUser(StartingLoginActivity.currentUser);
        String lastPlayedGame = user.getLastPlayedGame();

        switch (lastPlayedGame) {
            case Account.slidingName:
                filename = StartingLoginActivity.SAVE_SLIDING_SCOREBOARD;
                break;
            case Account.hangmanName:
                filename = StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD;
                break;
            case Account.sudokuName:
                filename = StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD;
                break;
            default:
                filename = StartingLoginActivity.SAVE_SUDOKU_SCOREBOARD;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findFilename();
        scoreBoard = fileSystem.loadScoreboard(this, filename);
        int score = scoreBoard.getLatestScore();
        TextView scoreTxt = findViewById(R.id.scoreText);
        String scoreMessage = "Your Score: " + String.valueOf(score);
        scoreTxt.setText(scoreMessage);
    }

    /**
     * Activate the Back Button.
     */
    private void addBackButtonListener() {
        Button saveButton = findViewById(R.id.backButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameMenu();
            }
        });
    }

    /**
     * Switch to the SlidingTilesMenu to start a new game.
     */
    private void switchToGameMenu() {
        Intent tmp = new Intent(this, ChooseGameMenuActivity.class);
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        switchToGameMenu();
    }
}
