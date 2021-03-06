package fall2018.csc2017.GameCentre;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class NotRobotActivityControllerTest {

    private ArrayList<Button> tileButtons;
    private SlidingTilesBoardManager manager;
    private NotRobotActivity act;
    private NotRobotActivityController controller;

    @Before
    public void setUp(){
        SlidingTileComplexityActivity.complexity = 2;
        Board.numRows = 2;
        Board.numCols = 2;
        controller = new NotRobotActivityController();
        tileButtons = new ArrayList<>();
        manager = new SlidingTilesBoardManager();
        act = new NotRobotActivity();
    }

    @Test
    public void createTileTest() {
        controller.createTileButtons(act, manager, tileButtons);
        assertEquals(4, tileButtons.size());
    }
}

