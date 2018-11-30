package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IsSolvableEvenTest {

    /**
     * The board used for this test.
     */
    Board board;

    /**
     * The board manager.
     */
    SlidingTilesBoardManager boardManager;

    /**
     * Create the even board where the blank is on the even row and has odd number of inversions.
     * This board is solvable.b
     */
    private Board solvableEvenBoardBlankOnEven() {
        Tile[][] tiles = new Tile[2][2];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(3);
        tiles[1][0] = new Tile(2);
        tiles[1][1] = new Tile(1);
        Board retBoard = new Board();
        retBoard.setTiles(tiles);
        return retBoard;
    }

    /**
     * Create the even board where the blank is on the even row and has even number of inversions.
     * This board is unsolvable.
     */
    private Board unsolvableEvenBoardBlankOnEven() {
        Tile[][] tiles = new Tile[2][2];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(3);
        tiles[1][0] = new Tile(1);
        tiles[1][1] = new Tile(2);
        Board retBoard = new Board();
        retBoard.setTiles(tiles);
        return retBoard;
    }

    /**
     * Create the even board where the blank is on the odd row and has an even number of inversions.
     * This is a solvable board.
     */
    private Board solvableEvenBoardBlankOnOdd() {
        Tile[][] tiles = new Tile[2][2];
        tiles[0][0] = new Tile(1);
        tiles[0][1] = new Tile(2);
        tiles[1][0] = new Tile(3);
        tiles[1][1] = new Tile(0);
        Board retBoard = new Board();
        retBoard.setTiles(tiles);
        return retBoard;
    }

    /**
     * Create the even board where the blank is on the odd row and has an odd number of inversions.
     * This is an unsolvable board.
     */
    private Board unsolvableEvenBoardBlankOnOdd() {
        Tile[][] tiles = new Tile[2][2];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(2);
        tiles[1][0] = new Tile(1);
        tiles[1][1] = new Tile(3);
        Board retBoard = new Board();
        retBoard.setTiles(tiles);
        return retBoard;
    }

    /**
     * Create an even board which is already solved.
     */
    private Board solvedEvenBoard() {
        Tile[][] tiles = new Tile[2][2];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(1);
        tiles[1][0] = new Tile(2);
        tiles[1][1] = new Tile(3);
        Board retBoard = new Board();
        retBoard.setTiles(tiles);
        return retBoard;
    }

    /**
     * Set the complexity for the test.
     */
    @Before
    public void setComplexity(){
        SlidingTileComplexityActivity.complexity = 2;
    }

    /**
     * Test whether or not the even board is solvable.
     */
    @Test
    public void testIsSolvableEvenSizedBoard() {
        boardManager = new SlidingTilesBoardManager();

        board = solvableEvenBoardBlankOnEven();
        boardManager.setBoard(board);
        assertTrue(boardManager.isSolvable());

        board = unsolvableEvenBoardBlankOnEven();
        boardManager.setBoard(board);
        assertFalse(boardManager.isSolvable());

        board = solvableEvenBoardBlankOnOdd();
        boardManager.setBoard(board);
        assertTrue(boardManager.isSolvable());

        board = unsolvableEvenBoardBlankOnOdd();
        boardManager.setBoard(board);
        assertFalse(boardManager.isSolvable());

        board = solvedEvenBoard();
        boardManager.setBoard(board);
        assertTrue(boardManager.isSolvable());
    }
}
