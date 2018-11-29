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
        setContentView(R.layout.activity_loosing);
        findFilename();
        scoreBoard = fileSystem.loadScoreboard(this, filename);
        int score = scoreBoard.getLatestScore();
        TextView scoreTxt = findViewById(R.id.scoreTextA);
        TextView wordMissed = findViewById(R.id.word);


        String scoreMessage = "Your Score: " + String.valueOf(score);
        String wordMessage = "The word was: " + WordManager.stringWord.toUpperCase() + " !";
        scoreTxt.setText(scoreMessage);
        wordMissed.setText(wordMessage);
        addBackButtonListener();
    }


    private void findFilename(){

        AccountManager accountManager = fileSystem.loadAccount(this);
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
        TextView scoreTxt = findViewById(R.id.scoreTextA);
        String scoreMessage = "Your Score: " + String.valueOf(score);
        scoreTxt.setText(scoreMessage);
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

    @Override
    public void onBackPressed() {
        switchToGameMenu();
    }
}
