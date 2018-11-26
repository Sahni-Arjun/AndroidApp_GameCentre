package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        loadFromFile();
        int score = scoreBoard.getLatestScore();
        TextView scoreTxt = findViewById(R.id.scoreText);
        String scoreMessage = "Your Score: " + String.valueOf(score);
        scoreTxt.setText(scoreMessage);
        addBackButtonListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();
        int score = scoreBoard.getLatestScore();
        TextView scoreTxt = findViewById(R.id.scoreText);
        String scoreMessage = "Your Score: " + String.valueOf(score);
        scoreTxt.setText(scoreMessage);
    }

    /**
     * Load scoreboard from fileName.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(StartingLoginActivity.SAVE_SCOREBOARD);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                scoreBoard = (Scoreboard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected " +
                    "data type: " + e.toString());
        }
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
