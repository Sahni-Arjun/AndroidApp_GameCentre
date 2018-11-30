package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SaveManagerTest {
    private SaveManager saveManager;

    /**
     * Make a set of tiles:
     * 1 2 3 // 9 is the blank tile.
     * 4 5 6
     * 7 8 9
     *
     * @return a set of tiles
     */
    private Tile[][] makeTiles() {
        SlidingTileComplexityActivity.complexity = 3;
        Tile[][] tiles = new Tile[3][3];

        tiles[0][0] = new Tile(1, 0);
        tiles[0][1] = new Tile(2, 1);
        tiles[0][2] = new Tile(3, 2);
        tiles[1][0] = new Tile(4, 3);
        tiles[1][1] = new Tile(5, 4);
        tiles[1][2] = new Tile(6, 5);
        tiles[2][0] = new Tile(7, 6);
        tiles[2][1] = new Tile(8, 7);
        tiles[2][2] = new Tile(9, 8);
        return tiles;
    }

    /**
     * Set up letters.
     */
    private Letter[][] makeLetters() {

        Letter[][] letters = new Letter[1][4];

        char[] splittedWord = "door".toCharArray();

        for (int count = 0; count < splittedWord.length; count++) {

            int letterNum = Character.toUpperCase(splittedWord[count]);
            letters[0][count] = new Letter(letterNum);

        }

        return letters;
    }

    @Before
    public void setUp() {
        saveManager = new SaveManager();
        SlidingTileComplexityActivity.complexity = 4;
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        Tile[][] tiles = makeTiles();
        boardManager.getBoard().setTiles(tiles);

        GameState state = new SlidingTilesState(boardManager, 0, 4, 3, 0, false);
        saveManager.addState(state);
        saveManager.updateSave(SaveManager.perma);
    }

    @Test
    public void getLastState() {
        SlidingTilesState autoState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        assertEquals(0, autoState.getNumMoves());
        assertFalse(autoState.getUnlimitedUndo());
        assertEquals(4, autoState.getComplexity());
        assertFalse(autoState.getUnlimitedUndo());

        SlidingTilesState permaState = (SlidingTilesState) saveManager.getLastState(SaveManager.perma);
        assertEquals(0, permaState.getNumMoves());
        assertFalse(permaState.getUnlimitedUndo());
        assertEquals(4, permaState.getComplexity());
        assertFalse(permaState.getUnlimitedUndo());
    }

    @Test
    public void addState() {
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        GameState state = new SlidingTilesState(boardManager, 1, 4, 3, 1, false);
        saveManager.addState(state);

        int numState = saveManager.getLength(SaveManager.auto);
        assertEquals(2, numState);

        SlidingTilesState autoState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        assertEquals(1, autoState.getNumMoves());
        assertFalse(autoState.getUnlimitedUndo());
        assertEquals(4, autoState.getComplexity());
        assertFalse(autoState.getUnlimitedUndo());
    }

    @Test
    public void updateSave() {
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        GameState state = new SlidingTilesState(boardManager, 1, 4, 3, 1, false);
        saveManager.addState(state);
        saveManager.updateSave(SaveManager.auto);

        SlidingTilesState autoState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        SlidingTilesState permaState = (SlidingTilesState) saveManager.getLastState(SaveManager.perma);

        assertEquals(permaState, autoState);

        boardManager = new SlidingTilesBoardManager();
        state = new SlidingTilesState(boardManager, 1, 4, 3, 1, false);
        saveManager.addState(state);
        saveManager.updateSave(SaveManager.perma);

        autoState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        permaState = (SlidingTilesState) saveManager.getLastState(SaveManager.perma);

        assertEquals(autoState, permaState);
    }

    @Test
    public void wipeSave() {
        saveManager.wipeSave(SaveManager.auto);

        assertEquals(0, saveManager.getLength(SaveManager.auto));

        saveManager.wipeSave(SaveManager.perma);

        assertEquals(0, saveManager.getLength(SaveManager.perma));

    }

    @Test
    public void undo() {
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        GameState state = new SlidingTilesState(boardManager, 1, 4, 3, 1, false);
        saveManager.addState(state);

        saveManager.undo();

        SlidingTilesState autoState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);

        assertEquals(0, autoState.getNumMoves());
        assertFalse(autoState.getUnlimitedUndo());
        assertEquals(4, autoState.getComplexity());
        assertFalse(autoState.getUnlimitedUndo());
    }

    @Test
    public void getLength() {
        assertEquals(1, saveManager.getLength(SaveManager.auto));
        assertEquals(1, saveManager.getLength(SaveManager.perma));
        saveManager.undo();
        assertEquals(0, saveManager.getLength(SaveManager.auto));
    }

    @Test
    public void getFinalScore() {
        SlidingTilesBoardManager boardManager3 = new SlidingTilesBoardManager();
        GameState state = new SlidingTilesState(boardManager3, 30, 3, 3, 1, false);
        saveManager.addState(state);
        assertEquals(212, saveManager.getFinalScore(SaveManager.slidingTilesName));

        SlidingTilesBoardManager boardManager4 = new SlidingTilesBoardManager();
        state = new SlidingTilesState(boardManager4, 30, 4, 3, 1, false);
        saveManager.addState(state);
        assertEquals(223, saveManager.getFinalScore(SaveManager.slidingTilesName));

        SlidingTilesBoardManager boardManager5 = new SlidingTilesBoardManager();
        state = new SlidingTilesState(boardManager5, 30, 5, 5, 1, false);
        saveManager.addState(state);
        assertEquals(2222, saveManager.getFinalScore(SaveManager.slidingTilesName));


        SudokuDifficultyActivity.difficulty = 3;
        SudokuBoardManager boardManagerSudoku3 = new SudokuBoardManager();
        state = new SudokuState(boardManagerSudoku3, 30, 3, 0, 0, true, 10000);
        saveManager.addState(state);
        assertEquals(2950, saveManager.getFinalScore(SaveManager.sudokuName));

        SudokuDifficultyActivity.difficulty = 2;
        SudokuBoardManager boardManagerSudoku2 = new SudokuBoardManager();
        state = new SudokuState(boardManagerSudoku2, 30, 2, 0, 0, true, 10000);
        saveManager.addState(state);
        assertEquals(975, saveManager.getFinalScore(SaveManager.sudokuName));

        SudokuDifficultyActivity.difficulty = 1;
        SudokuBoardManager boardManagerSudoku1 = new SudokuBoardManager();
        state = new SudokuState(boardManagerSudoku1, 30, 1, 0, 0, true, 10000);
        saveManager.addState(state);
        assertEquals(452, saveManager.getFinalScore(SaveManager.sudokuName));


        SudokuDifficultyActivity.difficulty = 3;
        WordManager wordManager3 = new WordManager(new Word());
        state = new HangmanState(wordManager3, 3, 3, 0, 3, false);
        saveManager.addState(state);
        assertEquals(459, saveManager.getFinalScore(SaveManager.hangmanName));

        SudokuDifficultyActivity.difficulty = 4;
        WordManager wordManager4 = new WordManager(new Word());
        state = new HangmanState(wordManager4, 3, 4, 0, 3, false);
        saveManager.addState(state);
        assertEquals(861, saveManager.getFinalScore(SaveManager.hangmanName));

        SudokuDifficultyActivity.difficulty = 5;
        WordManager wordManager5 = new WordManager(new Word());
        state = new HangmanState(wordManager5, 3, 5, 0, 3, false);
        saveManager.addState(state);
        assertEquals(2911, saveManager.getFinalScore(SaveManager.hangmanName));
    }

    @Test
    public void updateState() {
        //test sliding tiles
        saveManager.updateState(SaveManager.slidingTilesName, new SlidingTilesBoardManager());
        SlidingTilesState state = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        assertEquals(1, state.getNumMoves());
        assertEquals(3, state.getComplexity());
        assertFalse(state.getUnlimitedUndo());

        // test sudoku
        saveManager = new SaveManager();
        SudokuDifficultyActivity.difficulty = 3;
        SudokuBoardManager boardManagerSudoku = new SudokuBoardManager();
        GameState state1 = new SudokuState(boardManagerSudoku, 30, 1, 0, 0, true, 10000);
        saveManager.addState(state1);
        SudokuState state2 = (SudokuState) saveManager.getLastState(SaveManager.auto);

        saveManager.updateState(SaveManager.sudokuName, boardManagerSudoku);
        assertEquals(10000, state2.getTime());
        assertEquals(1, state2.getDifficulty());
        assertTrue(state2.getUnlimitedUndo());

        // test hangman
        saveManager = new SaveManager();
        HangmanComplexityActivity.complexity = 3;
        WordManager wordManager = new WordManager(new Word());
        GameState state3 = new HangmanState(wordManager, 3, 5, 0, 3, false);
        saveManager.addState(state3);
        HangmanState state4 = (HangmanState) saveManager.getLastState(SaveManager.auto);

        saveManager.updateState(SaveManager.hangmanName, wordManager);
        assertEquals(3, state4.getNumMoves());
        assertEquals(5, state4.getComplexity());
        assertFalse(state4.getUnlimitedUndo());
    }

    @Test
    public void undoMove() {
        SlidingTilesBoardManager boardManager3 = new SlidingTilesBoardManager();
        GameState state = new SlidingTilesState(boardManager3, 30, 3, 3, 1, false);
        saveManager.addState(state);

        boolean undone = saveManager.undoMove(SaveManager.slidingTilesName);
        assertTrue(undone);
        undone = saveManager.undoMove(SaveManager.slidingTilesName);
        assertFalse(undone);


        saveManager = new SaveManager();
        SudokuDifficultyActivity.difficulty = 3;
        SudokuBoardManager boardManagerSudoku = new SudokuBoardManager();
        GameState state1 = new SudokuState(boardManagerSudoku, 30, 1, 0, 0, true, 10000);
        saveManager.addState(state1);
        saveManager.addState(state1);

        undone = saveManager.undoMove(SaveManager.sudokuName);
        assertTrue(undone);
        undone = saveManager.undoMove(SaveManager.sudokuName);
        assertFalse(undone);
    }

    @Test
    public void getboardArrangement() {
        Tile[][] tiles = saveManager.getboardArrangement();
        Tile[][] expectedTiles = makeTiles();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(expectedTiles[i][j].getBackground(), tiles[i][j].getBackground());
            }
        }
    }

    @Test
    public void getWordArrangement() {
        saveManager = new SaveManager();

        List<Letter> expectedWord = new ArrayList<>();
        expectedWord.add(new Letter(68));
        expectedWord.add(new Letter(79));
        expectedWord.add(new Letter(79));
        expectedWord.add(new Letter(82));

        Word.numCols = 4;
        WordManager wordManager = new WordManager(new Word(expectedWord));
        GameState state3 = new HangmanState(wordManager, 3, 5, 0, 3, false);
        saveManager.addState(state3);

        Letter[][] letters = saveManager.getWordArrangement();
        Letter[][] expectedLetters = makeLetters();
        for (int j = 0; j < 3; j++) {
            assertEquals(expectedLetters[0][j].getBackground(), letters[0][j].getBackground());
        }

    }
}