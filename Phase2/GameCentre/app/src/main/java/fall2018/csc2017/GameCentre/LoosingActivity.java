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
     * The file system.
     */
    private FileSystem fileSystem = new FileSystem();

    private String filename;

    public static String scoreMessage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loosing);
        findFilename();
        TextView scoreTxt = findViewById(R.id.scoreTextA);
        TextView wordMissed = findViewById(R.id.word);


        scoreMessage = getScoreMessage();
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


    public static String getScoreMessage(){

        return scoreMessage;
    }


    public static void lost(){

        scoreMessage = "Your score is 0 !";
    }



    @Override
    protected void onResume() {

        super.onResume();
        findFilename();
        TextView scoreTxt = findViewById(R.id.scoreTextA);
        String scoreMessage = getScoreMessage();
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
