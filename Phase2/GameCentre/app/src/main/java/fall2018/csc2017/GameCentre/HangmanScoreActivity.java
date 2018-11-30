/*
View
 */
package fall2018.csc2017.GameCentre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * The Hangman scoreboard activity.
 */
public class HangmanScoreActivity extends AppCompatActivity {

    /**
     * The scoreboard for the Hangman game.
     */
    Scoreboard hangmanScoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_score);
        loadFromFile();
        TextView hangmanScores = findViewById(R.id.hangmanScores);
        hangmanScores.setText(hangmanScoreboard.createTopScoreText());
    }

    /**
     * Load the word manager from fileName.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                hangmanScoreboard = (Scoreboard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
}