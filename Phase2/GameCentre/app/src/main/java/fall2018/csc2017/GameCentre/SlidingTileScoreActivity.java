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
 * The sliding tile scoreboard activity.
 */
public class SlidingTileScoreActivity extends AppCompatActivity {

    /**
     * The scoreboard for the sliding tile game.
     */
    Scoreboard slidingTileScoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tile_score);
        loadFromFile();
        TextView slidingTileScores = findViewById(R.id.sldingTilesScores);
        slidingTileScores.setText(slidingTileScoreboard.getTopScores());
    }

    /**
     * Load the board manager from fileName.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(StartingLoginActivity.SAVE_SCOREBOARD);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                slidingTileScoreboard = (Scoreboard) input.readObject();
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
