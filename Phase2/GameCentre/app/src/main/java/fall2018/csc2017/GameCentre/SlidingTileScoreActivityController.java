package fall2018.csc2017.GameCentre;

import android.content.Context;

public class SlidingTileScoreActivityController {
    public StringBuilder onCreateListener(Context context) {
        Scoreboard slidingTileScoreboard;
        FileSystem fileSystem = new FileSystem();
        slidingTileScoreboard = fileSystem.loadScoreboard(context, StartingLoginActivity.SAVE_SLIDING_SCOREBOARD);
        return slidingTileScoreboard.createTopScoreText();
    }
}
