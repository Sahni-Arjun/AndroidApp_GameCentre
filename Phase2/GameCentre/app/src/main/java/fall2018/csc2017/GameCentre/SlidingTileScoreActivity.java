/*
View
 */
package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * The sliding tile scoreboard activity.
 */
public class SlidingTileScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tile_score);
        SlidingTileScoreActivityController controller = new SlidingTileScoreActivityController(new FileSystem());
        StringBuilder slidingTileScoreboard =  controller.onCreateListener(this);
        TextView slidingTileScores = findViewById(R.id.sldingTilesScores);
        slidingTileScores.setText(slidingTileScoreboard);
    }
}
