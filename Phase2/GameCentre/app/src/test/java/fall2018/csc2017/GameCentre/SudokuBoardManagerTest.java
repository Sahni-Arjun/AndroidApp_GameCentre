package fall2018.csc2017.GameCentre;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class SudokuBoardManagerTest {

    private SudokuBoardManager sudokuBoardManager;
    /**
     * Make a set of tiles:
     * 2 9 6 3 1 8 5 7 4
     * 5 8 4 9 7 2 6 1 3
     * 7 1 3 6 4 5 2 8 9
     * 6 2 5 8 9 7 3 4 1
     * 9 3 1 4 2 6 8 5 7
     * 4 7 8 5 3 1 9 2 6
     * 1 6 7 2 5 3 4 9 8
     * 8 5 9 7 6 4 1 3 2
     * 3 4 2 1 8 9 7 6 5
     * @return a set of tiles which comprise a solved sudoku board
     */
    private Tile[][] makeTiles() {

        Tile[][] tiles = new Tile[9][9];

        tiles[0][0] = new Tile(1);
        tiles[0][1] = new Tile(8);
        tiles[0][2] = new Tile(5);
        tiles[0][3] = new Tile(2);
        tiles[0][4] = new Tile(0);
        tiles[0][5] = new Tile(7);
        tiles[0][6] = new Tile(4);
        tiles[0][7] = new Tile(6);
        tiles[0][8] = new Tile(3);
        tiles[1][0] = new Tile(4);
        tiles[1][1] = new Tile(7);
        tiles[1][2] = new Tile(3);
        tiles[1][3] = new Tile(8);
        tiles[1][4] = new Tile(6);
        tiles[1][5] = new Tile(1);
        tiles[1][6] = new Tile(5);
        tiles[1][7] = new Tile(0);
        tiles[1][8] = new Tile(2);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(0);
        tiles[2][2] = new Tile(2);
        tiles[2][3] = new Tile(5);
        tiles[2][4] = new Tile(3);
        tiles[2][5] = new Tile(4);
        tiles[2][6] = new Tile(1);
        tiles[2][7] = new Tile(7);
        tiles[2][8] = new Tile(8);
        tiles[3][0] = new Tile(5);
        tiles[3][1] = new Tile(1);
        tiles[3][2] = new Tile(4);
        tiles[3][3] = new Tile(7);
        tiles[3][4] = new Tile(8);
        tiles[3][5] = new Tile(6);
        tiles[3][6] = new Tile(2);
        tiles[3][7] = new Tile(3);
        tiles[3][8] = new Tile(0);
        tiles[4][0] = new Tile(8);
        tiles[4][1] = new Tile(2);
        tiles[4][2] = new Tile(0);
        tiles[4][3] = new Tile(3);
        tiles[4][4] = new Tile(1);
        tiles[4][5] = new Tile(5);
        tiles[4][6] = new Tile(7);
        tiles[4][7] = new Tile(4);
        tiles[4][8] = new Tile(6);
        tiles[5][0] = new Tile(3);
        tiles[5][1] = new Tile(6);
        tiles[5][2] = new Tile(7);
        tiles[5][3] = new Tile(4);
        tiles[5][4] = new Tile(2);
        tiles[5][5] = new Tile(0);
        tiles[5][6] = new Tile(8);
        tiles[5][7] = new Tile(1);
        tiles[5][8] = new Tile(5);
        tiles[6][0] = new Tile(0);
        tiles[6][1] = new Tile(5);
        tiles[6][2] = new Tile(6);
        tiles[6][3] = new Tile(1);
        tiles[6][4] = new Tile(4);
        tiles[6][5] = new Tile(2);
        tiles[6][6] = new Tile(3);
        tiles[6][7] = new Tile(8);
        tiles[6][8] = new Tile(7);
        tiles[7][0] = new Tile(7);
        tiles[7][1] = new Tile(4);
        tiles[7][2] = new Tile(8);
        tiles[7][3] = new Tile(6);
        tiles[7][4] = new Tile(5);
        tiles[7][5] = new Tile(3);
        tiles[7][6] = new Tile(0);
        tiles[7][7] = new Tile(2);
        tiles[7][8] = new Tile(1);
        tiles[8][0] = new Tile(2);
        tiles[8][1] = new Tile(3);
        tiles[8][2] = new Tile(1);
        tiles[8][3] = new Tile(0);
        tiles[8][4] = new Tile(7);
        tiles[8][5] = new Tile(8);
        tiles[8][6] = new Tile(6);
        tiles[8][7] = new Tile(5);
        tiles[8][8] = new Tile(4);


        return tiles;
    }

    /**
     * Make a starting Board.
     */
    private void solvedBoard() {
        Tile[][] tiles = makeTiles();
        sudokuBoardManager = new SudokuBoardManager();
        sudokuBoardManager.setTiles(tiles);

    }

    private void blankBoard() {
        SudokuBoard board = new SudokuBoard();
        sudokuBoardManager = new SudokuBoardManager();
        sudokuBoardManager.setTiles(board.getTiles());
    }
    /**
     * Make 3 moves.
     */
    private int numBlankTile(Tile[][] tiles, int i, int j) {
        ArrayList<Tile> blankTile = new ArrayList<>();
        for (int i3 = i * 3; i3 < i * 3 + 3; i3++) {
            for (int j3 = j * 3; j3 < j * 3 + 3; j3++) {
                if (tiles[i3][j3].getId() == 0) {
                    blankTile.add(tiles[i3][j3]);
                }
            }
        }
        return blankTile.size();
    }


    @Before
    public void setUp(){
        SudokuActivity act = new SudokuActivity();
        SlidingTileComplexityActivity.complexity = 9;
        SudokuDifficultyActivity.difficulty = 1;
        }

    //puzzleSolved
    @Test
    public void puzzleSolvedFalseTest() {
        solvedBoard();
        Tile[][] tiles = sudokuBoardManager.getTiles();
        SudokuBoard board = new SudokuBoard();
        // by changing the value of any tile the sudoku rules are violated
        tiles[0][0] = new Tile(6);
        board.setTiles(tiles);
        sudokuBoardManager.setTiles(tiles);
        sudokuBoardManager.setBoard(board);
        assertFalse(sudokuBoardManager.puzzleSolved());
    }

    @Test
    public void puzzleSolvedFalse2Test() {
        solvedBoard();
        Tile[][] tiles = sudokuBoardManager.getTiles();
        // by changing the value of any tile the sudoku rules are violated
        tiles[0][0] = new Tile(-1);
        sudokuBoardManager.setTiles(tiles);
        assertFalse(sudokuBoardManager.puzzleSolved());
    }

    @Test
    public void puzzleSolvedFalse3Test() {
        solvedBoard();
        Tile[][] tiles = sudokuBoardManager.getTiles();
        // by changing the value of any tile the sudoku rules are violated
        tiles[0][0] = new Tile(4);
        tiles[1][0] = new Tile(1);
        tiles[0][6] = new Tile(1);
        tiles[2][6] = new Tile(4);
        sudokuBoardManager.setTiles(tiles);
        assertFalse(sudokuBoardManager.puzzleSolved());
    }
    @Test
    public void puzzleSolvedTrueTest() {
        solvedBoard();
        assertTrue(sudokuBoardManager.puzzleSolved());
    }


    /**
     * test that createSolvedBoard creates a solved sudokuboard that follows all the sudoku rules.
     */
    @Test
    public void sudokuCreateSolvedBoardTest() {
        SudokuDifficultyActivity.difficulty = 0;
        sudokuBoardManager = new SudokuBoardManager();
        assertTrue(sudokuBoardManager.puzzleSolved());


    }
    @Test
    public void sudokuBoardManagerConstructorTest() {
        SudokuDifficultyActivity.difficulty = 1;
        SudokuBoardManager boardManager = new SudokuBoardManager();
        SudokuBoard board = boardManager.getBoard();
        Tile[][] tiles = boardManager.getTiles();
        // Test if the number of tiles is 81
        assertEquals(81, board.numTiles());
        boolean correct = true;
        // test if there is 1 blank tile per 3x3 subsquare
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (numBlankTile(tiles, i, j) != 1) {
                    correct = false;
                }
            }
            assertTrue(correct);
        }
    }


    @Test
    public void getAvailableBlankTest() {
        blankBoard();
        final ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        // should contain every integer as all tiles are blank, so any integer would be valid
        ArrayList<Integer> available = sudokuBoardManager.getAvailable(0,0);
        assertEquals(expected,available);
    }

    @Test
    public void getAvailableSolvedTest() {
        solvedBoard();
        final ArrayList<Integer> expected = new ArrayList<>(Collections.singletonList(5));
        //should give an empty list as the board is solved so the only correct integer is the one
        //already there, in this case it is
        ArrayList<Integer> available = sudokuBoardManager.getAvailable(8,8);
        assertEquals(expected,available);
    }

    /**
     * check whether isValidTap returns true for a valid move
     */
    @Test
    public void isValidTapTrueTest() {
        SudokuActivity.currentNumber = 7;
        solvedBoard();
        Tile[][] tiles = sudokuBoardManager.getTiles();
        tiles[0][7] = new Tile(-1);
        sudokuBoardManager.setTiles(tiles);
        assertTrue(sudokuBoardManager.isValidTap(7));
    }

    /**
     * checks whether isValidTap returns false for an invalid move
     */
    @Test
    public void isValidTapFalseTest() {
        SudokuActivity.currentNumber = 3;
        solvedBoard();
        Tile[][] tiles = sudokuBoardManager.getTiles();
        tiles[0][7] = new Tile(-1);
        sudokuBoardManager.setTiles(tiles);
        assertFalse(sudokuBoardManager.isValidTap(7));
    }



    /**
     * moves when tile to change is blank and isValidTap is True
     */
    @Test
    public void touchMoveTrueTest() {
        SudokuActivity.currentNumber = 7;
        solvedBoard();
        Tile[][] tiles = sudokuBoardManager.getTiles();
        tiles[0][7] = new Tile(-1);
        sudokuBoardManager.setTiles(tiles);
        Tile newTile = new Tile(SudokuActivity.currentNumber - 1);
        sudokuBoardManager.touchMove(7);
        assertEquals(sudokuBoardManager.getTiles()[0][7].getId(),newTile.getId());
    }

    /**
     * does not move when tile to change is blank and isValidTap is false
     */
    @Test
    public void touchMoveNotValidTest() {
        SudokuActivity.currentNumber = 3;
        solvedBoard();
        Tile[][] tiles = sudokuBoardManager.getTiles();
        tiles[0][7] = new Tile(-1);
        sudokuBoardManager.setTiles(tiles);
        Tile newTile = new Tile(SudokuActivity.currentNumber - 1);
        sudokuBoardManager.touchMove(7);
        assertNotSame(sudokuBoardManager.getTiles()[0][7].getId(),newTile.getId());
    }
    /**
     * does not move when tile to change is not blank and isValidTap is true
     */
    @Test
    public void touchMoveNotBlankTest() {
        SudokuActivity.currentNumber = 7;
        blankBoard();
        Tile[][] tiles = sudokuBoardManager.getTiles();
        tiles[0][7] = new Tile(8);
        sudokuBoardManager.setTiles(tiles);
        Tile newTile = new Tile(SudokuActivity.currentNumber - 1);
        sudokuBoardManager.touchMove(7);
        assertNotSame(sudokuBoardManager.getTiles()[0][7].getId(),newTile.getId());
    }
    /**
     * does not move when tile to change is not blank and isValidTap is false
     */
    @Test
    public void touchMoveNotBlankNotValidTest() {
        SudokuActivity.currentNumber = 3;
        solvedBoard();
        Tile newTile = new Tile(SudokuActivity.currentNumber - 1);
        sudokuBoardManager.touchMove(7);
        assertNotSame(sudokuBoardManager.getTiles()[0][7].getId(),newTile.getId());
    }
}


