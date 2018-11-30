package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SaveManagerTest {
    private SaveManager saveManager;

    @Before
    public void setUp(){
        saveManager = new SaveManager();
        SlidingTileComplexityActivity.complexity = 4;
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        GameState state = new SlidingTilesState(boardManager, 0, 4, 3, 0, false );
        saveManager.addState(state);
    }

    @Test
    public void getLastState() {
        SlidingTilesState state = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        assertEquals(0, state.getNumMoves());
        assertFalse(state.getUnlimitedUndo());
        assertEquals(4, state.getComplexity());
        assertFalse(state.getUnlimitedUndo());
    }

    @Test
    public void addState() {
    }

    @Test
    public void updateSave() {
    }

    @Test
    public void wipeSave() {
    }

    @Test
    public void undo() {
    }

    @Test
    public void getLength() {
    }

    @Test
    public void getFinalScore() {
    }

    @Test
    public void updateState() {
    }

    @Test
    public void updateSudokuTime() {
    }

    @Test
    public void undoMove() {
    }

    @Test
    public void getboardArrangement() {
    }

    @Test
    public void getWordArrangement() {
    }
}