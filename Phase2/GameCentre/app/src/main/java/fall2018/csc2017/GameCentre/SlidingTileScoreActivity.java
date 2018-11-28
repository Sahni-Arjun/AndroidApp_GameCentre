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

    /**
     * The file system.
     */
    private FileSystem fileSystem = new FileSystem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tile_score);
        slidingTileScoreboard = fileSystem.loadScoreboard(this, StartingLoginActivity.SAVE_SLIDING_SCOREBOARD);
        TextView slidingTileScores = findViewById(R.id.sldingTilesScores);
        slidingTileScores.setText(slidingTileScoreboard.createTopScoreText());
    }
}
