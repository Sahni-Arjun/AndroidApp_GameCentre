package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreboardTest {

    private Scoreboard arbitraryScoreboard;

    @Before
    public void setUpScores() {
        arbitraryScoreboard =  new Scoreboard();

        arbitraryScoreboard.addToScoreBoard(arbitraryScoreboard.createScore("A", 10));
        arbitraryScoreboard.addToScoreBoard(arbitraryScoreboard.createScore("B", 8));
        arbitraryScoreboard.addToScoreBoard(arbitraryScoreboard.createScore("C", 8));
        arbitraryScoreboard.addToScoreBoard(arbitraryScoreboard.createScore("D", 0));
    }

    @Test
    public void testGetLatestScore() {
        assertEquals(arbitraryScoreboard.getLatestScore(), 0);
    }

    @Test
    public void testCreateTopScoreText() {
        String topScore = "10 A\n8 B\n8 C\n0 D\n";
        String retTopScore = arbitraryScoreboard.createTopScoreText().toString();
        assertEquals(retTopScore, topScore);
    }
}
