package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

public class SudokuScoreActivityControllerTest {
    /**
     * The slidingTileActivityController to test.
     */
    private SudokuScoreActivityController controller;

    @Before
    public void setUp(){
        final Scoreboard scoreboard = new Scoreboard();

        scoreboard.addToScoreBoard(scoreboard.createScore("Username", 100));
        scoreboard.addToScoreBoard(scoreboard.createScore("Student", 207));
        scoreboard.addToScoreBoard(scoreboard.createScore("Hello", 0));

        controller = new SudokuScoreActivityController(
                new FileSystem() {
                    public Scoreboard loadScoreboard(Context context, String filename) {
                        return scoreboard;
                    }
                }
        );
    }

    /**
     * Testing the on create listener returns the correct StringBuilder.
     */
    @Test
    public void testOnCreate(){
        Context context = new AppCompatActivity();
        StringBuilder scoreText = controller.onCreateListener(context);

        assert("207 Student\n100 Username\n0 Hello\n".contains(scoreText));
    }
}
